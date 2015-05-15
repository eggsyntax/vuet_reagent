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

(def default-value
  {:name "reagent vuet db"
   :zipper (h/new-zipper)
   })

(def db (atom default-value))

(defn flip-zipper [& args]
  (println "args " args)
  (swap! db assoc :zipper "new zipper!")
  (println @db))

(defn append-action [& args]
  (println "args " args)
  (swap! db assoc :zipper
         (h/append (:zipper @db)))
  (println @db))

(defn act-on-keypress [event & args]
  (let [lower-int (fn [v] (+ v 32))
        keychar (-> event .-which lower-int char)]
    (swap! db assoc :zipper 
           (h/act-on (:zipper @db) keychar)) 
    (println @db)))

(defn main []
 (fn [] 
   [:div 
    [:input {:on-click append-action 
             :on-key-down act-on-keypress}]
    [:str "Hello there!"]
    [:p]
    [:str (str (:zipper @db))]
    ])
  )
