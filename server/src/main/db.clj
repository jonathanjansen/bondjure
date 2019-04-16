(ns main.db)

(def db (atom {:calcs []}))

(defn add-calc [calc]
  (let [db-id (gensym)
        new-calc (assoc calc :id db-id)]
    (do (swap! db update-in [:calcs] #(conj % new-calc))
        new-calc)))
