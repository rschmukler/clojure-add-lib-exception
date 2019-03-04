(ns user
  (:require [clojure.tools.deps.alpha.repl :refer [add-lib]]))

(defn sync-libs
  "Dynamically refreshes all libs in `deps.edn` and installs any missing"
  ([] (sync-libs nil))
  ([?alias]
   (let [edn  (-> "deps.edn" slurp read-string)
         libs (if ?alias
                (get-in edn [:aliases ?alias :extra-deps])
                (get edn :deps))]
     (doseq [[lib-sym lib-spec] libs]
       (try
         (add-lib lib-sym lib-spec)
         (catch NullPointerException e
             (println "Error raised for:" lib-sym lib-spec)))))))

(comment
  (sync-libs)
  (add-lib 'clojure.java-time {:mvn/version "0.3.2"}))
