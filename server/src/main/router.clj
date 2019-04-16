(ns main.router
  (:require [main.db :as db]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route :as route]
            [clojure.data.json :as json]
            [main.routes :as web-routes]))

(defn get-calcs [request]
  {:status 200
   :body (json/write-str (@db/db :calcs))})

(def routes
  (route/expand-routes
   #{["/" :get web-routes/landing-page :route-name :web]
     ["/api/bond-calcs" :get get-calcs :route-name :bond-calc]
     ["/api/bond-calcs" :post #(str "post") :route-name :post-bond-calc]}))

