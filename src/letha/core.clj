(ns letha.core
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [jansi-clj.core :refer :all]
            [environ.core :refer [env]])
  (:use [clojure.string :only (join)])
  (:gen-class))

(defn -main
  [& args]
  (let [raw (nth args 0)
        keyfrom (env :youdao-keyfrom)
        key (env :youdao-key)
        response (client/get (format "http://fanyi.youdao.com/openapi.do?keyfrom=%s&key=%s&type=data&doctype=json&version=1.1&q=%s" keyfrom key raw))
        domain (blue (get-in response [:cookies "SESSION_FROM_COOKIE" :domain]))
        body (json/read-str (:body response))
        basic (get body "basic")
        showphonetic (fn [cate]
                       (magenta (str cate (#(str "[ " % " ]") (-> basic (get (str cate "-phonetic")))))))
        usphonetic (showphonetic "us")
        ukphonetic (showphonetic "uk")
        translate (cyan (join " " (get body "translation")))
        explains (green (join "\n" (map #(str "- " %) (-> basic (get "explains")))))
        phrases (join "\n" (map-indexed (fn [idx itm]
                                          (format "%d. %s\n   %s" idx
                                                  (let [key (-> itm (get "key"))
                                                        regexraw (re-pattern (str "(?ix) " raw))
                                                        findraw (re-find regexraw key)]
                                                    (clojure.string/replace key (re-pattern findraw) (yellow findraw)))
                                                  (red (join "," (-> itm (get "value")))))) (get body "web")))]
    (println keyfrom)
    (println key)
    (println (format "%s -> %s %s %s ~ %s\n" raw translate usphonetic ukphonetic domain))
    (println (str explains "\n"))
    (println (str phrases))))
