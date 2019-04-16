(ns main.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :results
 (fn [db _]
   (:results db)))

(re-frame/reg-sub
 :result
 (fn [db _]
   (:result db)))