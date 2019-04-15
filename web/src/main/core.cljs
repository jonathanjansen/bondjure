(ns main.core
  (:require [main.views :as views]
            [reagent.core :as reagent]
            [main.events :as events]
            [re-frame.core :refer [clear-subscription-cache! dispatch-sync  subscribe reg-event-db reg-event-fx]]))

(dispatch-sync [::events/initialize-db])

(clear-subscription-cache!)
(reagent/render [views/main-view]
                (.getElementById js/document "app"))

