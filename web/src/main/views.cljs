(ns main.views
  (:require [reagent.core  :as reagent]
            [re-frame.core :refer [subscribe dispatch]]
            [main.subs :as subs]
            [cljsjs.chartjs]))

(defn calculate [event values]
  (.preventDefault event)
  (dispatch [:calculate @values]))

(defn form-view []
  (reagent/with-let [values (reagent/atom nil)]
    [:form {:on-submit #(calculate % values)}
     [:div.form-group
      [:label {:for "person"} "Your Name"]
      [:input#purchase_price.form-control
       {:type "text"
        :on-change #(swap! values assoc :person (-> % .-target .-value))
        :placeholder "Your Name"}]]
     [:div.form-group
      [:label {:for "purchase_price"} "Purchase price"]
      [:input#purchase_price.form-control
       {:type "number"
        :on-change #(swap! values assoc :price (-> % .-target .-value))
        :placeholder "Purchase price"}]]
     [:div.form-group
      [:label {:for "deposit_paid"} "Deposit paid"]
      [:input#deposit_paid.form-control
       {:type "number"
        :on-change #(swap! values assoc :deposit (-> % .-target .-value))
        :placeholder "Deposit paid"}]]
     [:div.form-group
      [:label {:for "bond_term"} "Bond term in years"]
      [:input#bond_term.form-control
       {:type "number"
        :on-change #(swap! values assoc :term (-> % .-target .-value))
        :placeholder "Bond Term"}]]
     [:div.form-group
      [:label {:for "fixed_interest"} "Fixed interest rate"]
      [:input#fixed_interest.form-control
       {:type "text"
        :on-change #(swap! values assoc :interest (-> % .-target .-value))
        :placeholder "Fixed Interest Rate"}]]
     [:div.form-group
      [:button.btn.btn-primary.btn-lg.btn-block "Calculate"]]]))

(defn chart-fn [result]
  (fn [component]
    (let [context (.getContext (.getElementById js/document (str "chart-" (result :id))) "2d")
          chart-data {:type "bar"
                      :data {:labels (->> result :repayment-schedule (map :year))
                             :datasets [{:data (->> result :repayment-schedule (map :interest))
                                         :label "Interest %"
                                         :backgroundColor "#0DC4A3"}
                                        {:data (->> result :repayment-schedule (map :capital))
                                         :label "Capital %"
                                         :backgroundColor "#F4584D"}]}
                      :options {:scales {:xAxes [{:stacked true}]
                                         :yAxes [{:stacked true}]}}}]
      (js/Chart. context (clj->js chart-data)))))

(defn chart
  [result]
  (reagent/create-class
   {:component-did-mount (chart-fn result)
    :display-name        "chartjs-component"
    :reagent-render      (fn []
                           [:canvas {:id (str "chart-" (result :id)) :width "300" :height "100"}])}))

(defn result-view [results]
  [:div
   [:h2 "Prev results"]

   (for [[id result] results]
     [:div.card
      [:div.card-header (str (result :person) "'s bond calculation")]
      [:div.card-body {:id id}
       [:dl
        [:dt "Purchase price"]
        [:dd (result :price)]
        [:dt "Deposit paid"]
        [:dd (result :deposit)]
        [:dt "Bond term in years"]
        [:dd (result :interest)]
        [:dt "Fixed interest rate"]]]
      [:div.card-header "Repayment Shedule"]
      [:table.table
       [:thead
        [:tr
         [:th "Year"]
         [:th "Interest %"]
         [:th "Capital %"]]]
       [:tbody
        (for [sched (result :repayment-schedule)]
          [:tr
           [:td (sched :year)]
           [:td (sched :interest)]
           [:td (* (- 1 (/ (sched :interest) 100)) 100)]])]]
      [chart result]])])

(defn main-view []
  (let [results @(subscribe [:results])]
    [:div.container
     [:h1 "Bondjure"]
     (form-view)
     (result-view results)]))