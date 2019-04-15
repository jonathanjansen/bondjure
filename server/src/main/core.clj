(ns main.core
  (:require [io.pedestal.http :as http]
            [io.pedestal.http.ring-middlewares :as middlewares]
            [main.router :as router]))

(def service-map
  {::http/routes router/routes
   ::http/resource-path ""
   ::http/type :jetty
   ::http/secure-headers {:content-security-policy-settings {:object-src "none"}}
   ::http/port 3000})

(defn start []
  (http/start (http/create-server service-map)))

;; For interactive development
(defonce server (atom nil))

(defn start-dev []
  (reset! server
          (http/start (http/create-server
                       (assoc (-> service-map
                                  (http/default-interceptors))
                              ::http/join? false)))))

(defn stop-dev []
  (http/stop @server))

(defn restart []
  (stop-dev)
  (start-dev))

(comment
  (restart))

(start-dev)
