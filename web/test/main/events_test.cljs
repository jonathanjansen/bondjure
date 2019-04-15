(ns main.events-test
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [main.events :as events]))

(deftest test-numbers
  (is (= (events/repayment 200000 6.5 30 0) 1264.14))
  (is (= (events/repayment 2000000 10.25 20 0) 19632.87)))

