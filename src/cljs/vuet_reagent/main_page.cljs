(ns vuet-reagent.main-page
    (:require [reagent.core :as reagent :refer [atom]]
              [vuet-regent.huet :as h]              
;               [reagent.session :as session]
;               [secretary.core :as secretary :include-macros true]
;               [goog.events :as events]
;               [goog.history.EventType :as EventType]
;               [cljsjs.react :as react]
              )
    (:import goog.History))

(defonce default-db-value
  {:name "reagent vuet db"
   :zipper (h/new-zipper)})

; Lovely easy-access global variable. I mean database.
(defonce db (atom default-db-value))

(defn act-on-keypress [event & args]
  (let [lower-int (fn [v] (+ v 32))
        keychar (-> event .-which lower-int char)]
    (swap! db assoc :zipper 
           (h/act-on (:zipper @db) keychar)) 
    (println @db)))

(defn main []
 (fn [] 
   [:div 
    [:input {:on-key-down act-on-keypress}]
    [:p]
    [:str (str (:zipper @db))]
    ])
  )
