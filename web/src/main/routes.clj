(ns main.routes
  (:require
   [clojure.java.io :as io]
   [selmer.parser :as selmer]))

(defn landing-page [ctx]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (selmer/render-file
          "index.html"
          {:ctx ctx}
          {:custom-resource-path (io/resource "web")})})
