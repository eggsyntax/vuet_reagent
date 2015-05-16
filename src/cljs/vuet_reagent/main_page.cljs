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
  ; TODO only do + 32 if it's in the A..Z range
  (let [lower-int (fn [v] (+ v 32))
        keychar (-> event .-which lower-int char)]
    (println "keychar: " keychar)
    (swap! db assoc :zipper 
           (h/act-on (:zipper @db) keychar)) 
    (println @db)))

(defn z-node []
  [:svg {:height 100
           :width 500}
     [:ellipse {:cx 220
                :cy 50
                :rx 20
                :ry 20
                :style {:fill :yellow}
                }]
     ])

(defn main []
 (fn [] 
   [:div 
    [:input {:on-key-down act-on-keypress}]
    [:p]
    [:str (str (:zipper @db))]
    [z-node]
    ]))
