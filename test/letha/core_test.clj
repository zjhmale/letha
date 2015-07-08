(ns letha.core-test
  (:require [clojure.test :refer :all]
            [letha.core :refer :all]))

(deftest test-translate
  (testing "translate success"
    (trans "e.g."))
  (testing "translate failed"
    (trans "i.e.")))
