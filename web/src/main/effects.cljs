(ns main.effects
  (:require [re-frame.core :as rf :refer [dispatch reg-fx reg-event-fx]]))

(reg-fx
 :http
 (fn [{:keys [method
              url
              success-event
              error-event
              ignore-response-body
              ajax-map]
       :or {error-event [:ajax-error]
            ajax-map {}}}]
   (method url (merge
                {:response-format :json
                 :keywords? true
                 :format :json
                 :handler (fn [response]
                            (when success-event
                              (dispatch (if ignore-response-body
                                          success-event
                                          (conj success-event response))))
                            )
                 :error-handler (fn [error]
                                  (dispatch (conj error-event error))
                                  )}
                ajax-map))))

