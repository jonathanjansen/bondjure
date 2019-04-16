(ns main.events
  (:require [ajax.core :refer [GET POST]]
            [re-frame.core :refer [reg-event-db reg-event-fx]]))

(def default-db {:results {}})

(defn repayment [price rate term deposit]
  (let [monthly-intrest (/ rate 100 12)
        months (* term 12)]
    (-> (/ (* monthly-intrest (- price deposit))
           (- 1 (js/Math.pow (+ 1  monthly-intrest) (- months))))
        (* 100)
        (Math/round)
        (/ 100))))

(defn amort [initial-balance interest term-months monthly-pay]
  (loop [period 1
         time term-months
         balance initial-balance
         remaining initial-balance
         sch []]
    (let [principal (- (* (+ 1 (/ interest 100)) balance) (* 12 monthly-pay))
          interest-amount (/ (* balance interest) 100)
          interest-perc (* (- 1 (/ interest-amount (* monthly-pay 12))) 100)
          reduc (- (* monthly-pay 12) interest-amount)
          capital-perc (* (- 1 (/ interest-perc 100)) 100)
          cap (- remaining reduc)]
      (if (= time 0) sch
          (recur (inc period)
                 (dec time)
                 principal
                 cap
                 (conj sch {:year period :interest interest-perc :capital capital-perc}))))))

(reg-event-db
 :set-results
 (fn [db [_ results]]
   (assoc db :results (into {}
                            (map
                             #(assoc {} (% :id) %)
                             results )))))

(reg-event-fx
 ::initialize-db
 (fn [{:keys [db]} _]
   {:http {:method GET
           :url "/api/bond-calcs"
           :success-event [:set-results]}}))

(re-frame.core/reg-event-db
 :calculate
 (fn [db [_ values]]
   (let [db-id (gensym)
         price (values :price)
         interest (values :interest)
         term (values :term)
         repayment (repayment price interest term (values :deposit))]
     (assoc-in db [:results]
               (assoc (db :results) 
                      db-id (assoc values
                                   :id db-id
                                   :repayment repayment
                                   :repayment-schedule (amort price interest term repayment)))))))