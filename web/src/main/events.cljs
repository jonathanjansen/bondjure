(ns main.events
  (:require [re-frame.core :refer [reg-event-db reg-event-fx]]))

(def default-db {:results {1 {:person "Jonathan Jansen"
                              :price 1000000
                              :rate 10
                              :deposit 10000
                              :term 20
                              :repayment-schedule [{:year 1 :intrest 1 :capital 99}
                                                   {:year 2 :intrest 2 :capital 98}
                                                   {:year 3 :intrest 5 :capital 95}]}}})

(reg-event-fx
 ::initialize-db
 (fn [{:keys [db]} _]
   {:db default-db}))

(re-frame.core/reg-event-db
 :calculate
 (fn [db [_ values]]
   (let [db-id (gensym)]
     (assoc-in db [:results] (assoc (db :results) db-id values)))))