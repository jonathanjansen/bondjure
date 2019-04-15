(ns main.router
  (:require [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route :as route]
            [main.routes :as web-routes]))


(def routes
  (route/expand-routes
   #{["/" :get web-routes/landing-page :route-name :web]
     ["/api/bond-calc" :get #(str "get") :route-name :bond-calc]
     ["/api/bond-calc" :post #(str "post") :route-name :post-bond-calc]}))

