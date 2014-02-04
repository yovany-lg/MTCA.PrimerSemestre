(use 'clojure.core.logic)
(use 'clojure.core.logic.pldb)

;(defrel padre Padre Hijo)
;(defrel madre Madre Hijo)
;(defrel hombre H)
(db-rel padre p h)
(db-rel madre m h)
(db-rel hombre h)

(defn padres [p h]
    "Retorna tanto al padre como la madre"
    (conde
        ((padre p h) s#)
        ((madre p h) s#)))

(defn hijo [h p]
    "Relación Hijo Padre"
    (conde
        ((padre p h) s#)
        ((madre p h) s#)))

(defn abuelo [ab ni]
    "Para obtener la relación de abuelo nieto"
    (fresh [p]
        (padre p ni)
        (padre ab p)))

(defn abuela [ab ni]
    "Para obtener la relación de abuela nieto"
    (fresh [p]
        (padre p ni)
        (madre ab p)
        (nafc hombre ab)))

(defn hermano [h1 h2]
    "Retorna si un h1 es hermano (hombre) de h2"
    (fresh [p]
        (padre p h1)
        (padre p h2)
        (hombre h1)
        (!= h1 h2)))

(defn hermana [h1 h2]
    "Retorna si un h1 es hermana de h2"
    (fresh [p]
        (padre p h1)
        (padre p h2)
        (nafc hombre h1)
        (!= h1 h2)))


(def facts
    (db
        [padre :Cronus :Zeus] [padre :Cronus :Demeter] [padre :Cronus :Poseidon] [padre :Cronus :Hera] [padre :Cronus :Hades] [padre :Cronus :Hestia]
        [padre :Zeus :Athena] [padre :Zeus :Psersephone] [padre :Zeus :Hephaestus] [padre :Zeus :Hebe] [padre :Zeus :Ares] [padre :Zeus :Heracles] [padre :Zeus :Dionysus] [padre :Zeus :Aphrodite] [padre :Zeus :Apollo] [padre :Zeus :Artemis] [padre :Zeus :Hermes]
        [padre :Coeus :Leto] 
        [padre :Epimetheus :Dione]
        [padre :Atlas :Maia]
        [padre :Hermes :Tyche] [padre :Hermes :Rhodos] [padre :Hermes :Peitho] [padre :Hermes :Eunomia] [padre :Hermes :Hermaphroditus]

        [madre :Rhea :Zeus] [madre :Rhea :Demeter] [madre :Rhea :Poseidon] [madre :Rhea :Hera] [madre :Rhea :Hades] [madre :Rhea :Hestia]
        [madre :Demeter :Psersephone] [madre :Hera :Hephaestus] [madre :Hera :Hebe] [madre :Hera :Ares] [madre :Alcmene :Heracles] [madre :Semele :Dionysus] [madre :Dione :Aphrodite] [madre :Leto :Apollo] [madre :Leto :Artemis] [madre :Maia :Hermes]
        [madre :Phoebe :Leto] 
        [madre :Pleione :Maia]
        [madre :Aphrodite :Tyche] [madre :Aphrodite :Rhodos] [madre :Aphrodite :Peitho] [madre :Aphrodite :Eunomia] [madre :Aphrodite :Hermaphroditus]

        [hombre :Cronus]
        [hombre :Zeus]
        [hombre :Poseidon]
        [hombre :Hades]
        [hombre :Epimetheus]
        [hombre :Atlas]
        [hombre :Dionysus]
        [hombre :Heracles]
        [hombre :Ares]
        [hombre :Hephaestus]
        [hombre :Apollo]
        [hombre :Hermes]
        [hombre :Rhodos ]))

;Los papás de Zeus
(with-db facts
    (doall 
      (run* [q]
        (padres q :Zeus))))
;(:Cronus :Rhea)

;Los hijos de Zeus
(with-db facts
    (doall 
      (run* [q]
        (hijo q :Zeus))))
;(:Athena :Artemis :Hermes :Hephaestus :Heracles :Ares :Apollo :Psersephone :Hebe :Dionysus :Aphrodite)


;Abuelo
(with-db facts
    (doall 
      (run* [q]
        (abuelo q :Athena))))
;(:Cronus)

;Abuela
(with-db facts
    (doall 
      (run* [q]
        (abuela q :Athena))))
;(:Rhea)


;Los hermanos de Zeus
(with-db facts
    (doall 
      (run* [q]
        (hermano q :Zeus))))
;(:Hades :Poseidon)

;Los hermanas de Zeus
(with-db facts
    (doall 
      (run* [q]
        (hermana q :Zeus))))
;(:Hera :Demeter :Hestia)











;Old version < 0.8.5
(facts padre 
    [[:Cronus :Zeus] [:Cronus :Demeter] [:Cronus :Poseidon] [:Cronus :Hera] [:Cronus :Hades] [:Cronus :Hestia]
    [:Zeus :Athena] [:Zeus :Psersephone] [:Zeus :Hephaestus] [:Zeus :Hebe] [:Zeus :Ares] [:Zeus :Heracles] [:Zeus :Dionysus] [:Zeus :Aphrodite] [:Zeus :Apollo] [:Zeus :Artemis] [:Zeus :Hermes]
    [:Coeus :Leto] 
    [:Epimetheus :Dione]
    [:Atlas :Maia]
    [:Hermes :Tyche] [:Hermes :Rhodos] [:Hermes :Peitho] [:Hermes :Eunomia] [:Hermes :Hermaphroditus]])

(facts madre 
    [[:Rhea :Zeus] [:Rhea :Demeter] [:Rhea :Poseidon] [:Rhea :Hera] [:Rhea :Hades] [:Rhea :Hestia]
    [:Demeter :Psersephone] [:Hera :Hephaestus] [:Hera :Hebe] [:Hera :Ares] [:Alcmene :Heracles] [:Semele :Dionysus] [:Dione :Aphrodite] [:Leto :Apollo] [:Leto :Artemis] [:Maia :Hermes]
    [:Phoebe :Leto] 
    [:Pleione :Maia]
    [:Aphrodite :Tyche] [:Aphrodite :Rhodos] [:Aphrodite :Peitho] [:Aphrodite :Eunomia] [:Aphrodite :Hermaphroditus]])

(facts hombre
    (map list [:Cronus :Zeus :Poseidon :Hades :Epimetheus :Atlas :Dionysus :Heracles :Ares :Hephaestus :Apollo :Hermes :Rhodos ]))

(run* [q]
  (padres q :Zeus))

(run* [q]
  (madre q :Zeus))

(run* [q] (hermano :Hades :Hera) (== true q))

(run* [q] 
    (hermano q :Hades))

