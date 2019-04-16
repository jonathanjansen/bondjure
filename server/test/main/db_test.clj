(ns main.db-test
  (:require [clojure.test :refer [is testing deftest]]
            [main.db :as db]))

(def calc {:id 1
           :person "Jonathan Jansen"
           :price 1000000
           :rate 10
           :deposit 10000
           :term 20
           :repayment-schedule [{:year 1 :interest 1 :capital 99}
                                {:year 2 :interest 2 :capital 98}
                                {:year 3 :interest 5 :capital 95}]})



(deftest calc-crud
  (is (= (db/add-calc calc) calc)))
 

(db/add-calc calc)

@db/db
