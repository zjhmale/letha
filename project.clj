(defproject letha "0.1.0"
  :description "a command line directory"
  :url "https://github.com/zjhmale/letha"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.6"]
                 [clj-http "1.1.2"]
                 [jansi-clj "0.1.0"]
                 [environ "1.0.0"]]
  :main ^:skip-aot letha.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
