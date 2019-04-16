(ns main.db)

(def db (atom {:calcs []}))

(defn add-calc [calc]
  (do (swap! db update-in [:calcs] #(conj % calc))
      calc))
