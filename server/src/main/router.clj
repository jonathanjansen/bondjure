(ns main.router
  (:require [main.db :as db]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route :as route]
            [clojure.data.json :as json]
            [main.routes :as web-routes]))

(defn response [status body & {:as headers}]
  {:status status :body body :headers headers})

(def ok       (partial response 200))
(def created  (partial response 201))

(defn get-calcs [request]
  (ok (json/write-str (@db/db :calcs))))

(def post-bond-calc
  {:name :post-bond-calc
   :enter
   (fn [context]
     (let [bc (get-in context [:request :json-params])
           new-bc (db/add-calc bc)
           url (route/url-for :bond-calc :params {:id (new-bc :id)})]
       (assoc context
              :response (created (json/write-str  new-bc)
                                 "Location" url
                                 "Content-Type" "application/json")
              :tx-data new-bc)))})


(def routes
  (route/expand-routes
   #{["/" :get web-routes/landing-page :route-name :web]
     ["/api/bond-calcs" :get get-calcs :route-name :bond-calcs]
     ["/api/bond-calcs" :post [(body-params/body-params) post-bond-calc] :route-name :post-bond-calc]
     ["/api/bond-calcs/:id" :get #(str "get cal route") :route-name :bond-calc]}))

