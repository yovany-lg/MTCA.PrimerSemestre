(use 'clojure.core.logic)
(use 'clojure.core.logic.pldb)

(db-rel padre p h)
(db-rel madre m h)
(db-rel hombre h)

(defn padres [p h]
    "Restringe a la variable p para que sea el padre o la madre de h"
    (conde
        ((padre p h) s#)
        ((madre p h) s#)))

(defn esposo [He She]
    "Restringe a la variable He para que comparta al menos un hijo Son con la variable She"
    (fresh [Son]
        (padre He Son)
        (madre She Son)))

(defn esposa [She He]
    "Restringe a la variable She para que comparta al menos un hijo Son con la variable He"
    (fresh [Son]
        (padre He Son)
        (madre She Son)))

(defn cunado [c1 c2]
    "c1 tiene una relación de cuñado con c2 si el esposo(a) de c1 es hermano (genérico) de c2.
    c2 tiene una relación de cuñado con c2 si el esposo(a) de c1 es hermano (genérico) de c1."
    (fresh [esp]
        (conde
            ((esposo esp c1) (hermanos esp c2) s#)
            ((esposa esp c1) (hermanos esp c2) s#)
            ((esposo esp c2) (hermanos esp c1) s#)
            ((esposa esp c2) (hermanos esp c1) s#))))

(defn hijo [h p]
    "Relación Hijo Padre/Madre"
    (conde
        ((padre p h) s#)
        ((madre p h) s#)))

(defn sobrino [s t]
    "La relación sobrino de s a t existe cuando el padre o la madre de s es hermano con t."
    (fresh [h]
        (conde 
            ((padre h s) (hermanos h t) s#)
            ((madre h s) (hermanos h t) s#))))

(defn tio [t s]
    "La relación sobrino de s a t existe cuando el padre o la madre de s es hermano con t."
    (fresh [h]
        (conde 
            ((padre h s) (hermanos h t) s#)
            ((madre h s) (hermanos h t) s#))))

(defn tioSegundo [t s]
    "La relación se da cuando el padre de s es primo de t."
    (fresh [p]
        (conde
            ((padre p s) (primo p t) s#)
            ((madre p s) (primo p t) s#))))

(defn medioHermano [h1 h2]
    "La relación se da cuando h1 y h2 comparten uno y solo uno de sus padres."
    (fresh [p m]
        (!= h1 h2)
        (conde
            ((padre p h1) (padre p h2) (nafc hermanos h1 h2) s#)    ;La función nafc restringe a que la relación no exista
            ((madre m h1) (madre m h2) (nafc hermanos h1 h2) s#))))

(defn hermanos [h1 h2]
    "Relación de hermano genérica."
    (conde
        ((hermano h1 h2) s#)
        ((hermana h1 h2) s#)))

(defn hermano [h1 h2]
    "Retorna si un h1 es hermano (hombre) de h2"
    (fresh [p m]
        (padre p h1)
        (madre m h1)
        (padre p h2)
        (madre m h2)
        (hombre h1)
        (!= h1 h2)))

(defn hermana [h1 h2]
    "Retorna si un h1 es hermana de h2"
    (fresh [p m]
        (!= h1 h2)
        (nafc hombre h1)
        (padre p h1)
        (madre m h1)
        (padre p h2)
        (madre m h2)))

(defn primo [p1 p2]
    "Relación primo se da cuando los papás de p1 y p2 son hermanos."
    (fresh [p_p1 p_p2]
        (!= p1 p2)
        (conde
            ((padre p_p1 p1) (padre p_p2 p2) (hermanos p_p1 p_p2) s#)
            ((madre p_p1 p1) (madre p_p2 p2) (hermanos p_p1 p_p2) s#)
            ((padre p_p1 p1) (madre p_p2 p2) (hermanos p_p1 p_p2) s#)
            ((madre p_p1 p1) (padre p_p2 p2) (hermanos p_p1 p_p2) s#))))

(defn primoSegundo [p1 p2]
    "Relación primoSegundo se da cuando los papás de p1 y p2 son primos."
    (fresh [p_p1 p_p2]
        (!= p1 p2)
        (conde
            ((padre p_p1 p1) (padre p_p2 p2) (primo p_p1 p_p2) s#)
            ((madre p_p1 p1) (madre p_p2 p2) (primo p_p1 p_p2) s#)
            ((padre p_p1 p1) (madre p_p2 p2) (primo p_p1 p_p2) s#)
            ((madre p_p1 p1) (padre p_p2 p2) (primo p_p1 p_p2) s#))))

(defn abuelo [ab ni]
    "La relación de abuelo nieto se da cuando ab es padre de alguno de los padres de ni."
    (fresh [p]
        (conde
            ((padre p ni) (padre ab p) s#)
            ((madre p ni) (padre ab p) s#))))

(defn abuela [ab ni]
    "La relación de abuela nieto se da cuando ab es madre de alguno de los padres de ni."
    (fresh [p]
        (conde
            ((padre p ni) (madre ab p) s#)
            ((madre p ni) (madre ab p) s#))))

(defn abuelos [ab ni]
    "Relación abuelo de manera genérica."
    (conde 
        ((abuelo ab ni) s#)
        ((abuela ab ni) s#)))

(defn tioAbuelo [tab ni]
    "Cuando tab es hermano de algún abuelo de ni"
    (fresh [ab]
        (abuelos ab ni)
        (hermanos tab ab)))

(defn bisabuelo [ab ni]
    "Cuando ab es padre de algún abuelo de ni"
    (fresh [p]
        (conde
            ((abuelo p ni) (padre ab p) s#)
            ((abuela p ni) (padre ab p) s#))))

(defn bisabuela [ab ni]
    "Cuando ab es madre de algún abuelo de ni"
    (fresh [p]
        (conde
            ((abuelo p ni) (madre ab p) s#)
            ((abuela p ni) (madre ab p) s#))))

(defn bisabuelos [ab ni]
    "Relación bisabluelos de manera genérica."
    (conde
        ((bisabuelo ab ni) s#)
        ((bisabuela ab ni) s#)))

(defn suegro [sueg yerNu]
    "Cuando sueg es padre del (la) esposo(a) de yerNu."
    (fresh [esp]
        (conde
            ((esposa esp yerNu) (padre sueg esp) s#)
            ((esposo esp yerNu) (padre sueg esp) s#))))

(defn suegra [sueg yerNu]
    "Cuando sueg es madre del (la) esposo(a) de yerNu."
    (fresh [esp]
        (conde
            ((esposa esp yerNu) (madre sueg esp) s#)
            ((esposo esp yerNu) (madre sueg esp) s#))))

(defn suegros [sueg yerNu]
    "Relación suegro de manera genérica."
    (conde
        ((suegro sueg yerNu) s#)
        ((suegra sueg yerNu) s#)))

(defn nuera [nu sueg]
    "La relación se da cuando el esposo de nu es hijo de sueg."
    (fresh [hi]
        (esposo hi nu)
        (hijo hi sueg)))

(defn yerno [yer sueg]
    "La relación se da cuando la esposa de yer es hija de sueg."
    (fresh [hi]
        (esposa hi yer)
        (hijo hi sueg)))

(def facts  
    (db-rel ;Deficinición de la base de datos
        ;Todas las relaciones de padre
        [padre :Uranus :Cronus] [padre :Uranus :Rhea] [padre :Uranus :Coeus] [padre :Uranus :Phoebe] [padre :Uranus :Iapetus] [padre :Uranus :Oceanus] [padre :Uranus :Tethys]
        [padre :Cronus :Zeus] [padre :Cronus :Demeter] [padre :Cronus :Poseidon] [padre :Cronus :Hera] [padre :Cronus :Hades] [padre :Cronus :Hestia]
        [padre :Zeus :Athena] [padre :Zeus :Persephone] [padre :Zeus :Hephaestus] [padre :Zeus :Hebe] [padre :Zeus :Ares] [padre :Zeus :Heracles] [padre :Zeus :Dionysus] [padre :Zeus :Aphrodite] [padre :Zeus :Apollo] [padre :Zeus :Artemis] [padre :Zeus :Hermes]
        [padre :Coeus :Leto] 
        [padre :Iapetus :Atlas]  [padre :Iapetus :Epimetheus] [padre :Epimetheus :Dione] [padre :Atlas :Maia]
        [padre :Oceanus :Pleione]
        [padre :Hermes :Tyche] [padre :Hermes :Rhodos] [padre :Hermes :Peitho] [padre :Hermes :Eunomia] [padre :Hermes :Hermaphroditus]
        ;Todas las relaciones de madre
        [madre :Gaia :Cronus] [madre :Gaia :Rhea] [madre :Gaia :Coeus] [madre :Gaia :Phoebe] [madre :Gaia :Iapetus] [madre :Gaia :Oceanus] [madre :Gaia :Tethys]
        [madre :Rhea :Zeus] [madre :Rhea :Demeter] [madre :Rhea :Poseidon] [madre :Rhea :Hera] [madre :Rhea :Hades] [madre :Rhea :Hestia]
        [madre :Demeter :Persephone] [madre :Hera :Hephaestus] [madre :Hera :Hebe] [madre :Hera :Ares] [madre :Alcmene :Heracles] [madre :Semele :Dionysus] [madre :Dione :Aphrodite] [madre :Leto :Apollo] [madre :Leto :Artemis] [madre :Maia :Hermes]
        [madre :Phoebe :Leto] 
        [madre :Tethys :Pleione][madre :Pleione :Maia]
        [madre :Aphrodite :Tyche] [madre :Aphrodite :Rhodos] [madre :Aphrodite :Peitho] [madre :Aphrodite :Eunomia] [madre :Aphrodite :Hermaphroditus]
        ;Todos los que son hombres
        [hombre :Uranus] [hombre :Cronus] [hombre :Coeus] [hombre :Iapetus] [hombre :Oceanus]
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

;Códigos para obtener las relaciones de manera correcta

;Papas
(with-db facts
    (doall 
      (run* [q]
        (padres q :Zeus))))
;(:Cronus :Rhea)

;Hijos
(with-db facts
    (doall 
      (run* [q]
        (hijo q :Zeus))))
;(:Athena :Artemis :Hermes :Hephaestus :Heracles :Ares :Apollo :Psersephone :Hebe :Dionysus :Aphrodite)

;Abuelos (Hombres)
(with-db facts
    (doall 
      (run* [q]
        (abuelo q :Apollo))))
;(:Cronus :Coeus)

;Abuelas
(with-db facts
    (doall 
      (run* [q]
        (abuela q :Apollo))))
;(:Rhea :Phoebe)

;Abuelos (en general)
(with-db facts
    (doall 
      (distinct (run* [q]
        (abuelos q :Tyche)))))
;(:Zeus :Maia :Dione)

;Bisabuelos
(with-db facts
    (doall 
      (distinct (run* [q]
        (bisabuelos q :Tyche)))))
;(:Cronus :Rhea :Atlas :Pleione :Epimetheus)

;Nietos
(with-db facts
    (doall 
      (distinct (run* [q]
        (abuelo :Uranus q)))))
;(:Epimetheus :Pleione :Zeus :Leto :Atlas :Hera :Demeter :Hades :Poseidon :Hestia)

;Bisnietos
(with-db facts
    (doall 
      (distinct (run* [q]
        (bisabuelo :Uranus q)))))
;(:Hephaestus :Apollo :Athena :Artemis :Hermes :Heracles :Ares :Maia :Psersephone :Dione :Hebe :Dionysus :Aphrodite)

;Hermanos (Hombres)
(with-db facts
    (doall 
      (run* [q]
        (hermano q :Zeus))))
;(:Hades :Poseidon)

;Hermanas
(with-db facts
    (doall 
      (run* [q]
        (hermana q :Zeus))))
;(:Hera :Demeter :Hestia)

;Hermanos (en general)
(with-db facts
    (doall 
      (run* [q]
        (hermanos q :Tyche))))
;(:Hermaphroditus :Peitho :Rhodos :Eunomia)

;Primos
(with-db facts
    (doall 
      (distinct (run* [q]   ;Se usa distinct para seleccionar solo una instancia (de otras repetidas)
        (primo :Leto q)))))
;(:Epimetheus :Pleione :Zeus :Atlas :Hera :Demeter :Hades :Hestia :Poseidon)

;Primo Segundo
(with-db facts
    (doall 
      (distinct (run* [q] 
        (primoSegundo :Artemis q)))))
;(:Hephaestus :Maia :Apollo :Persephone :Dione :Athena :Hermes :Ares :Heracles :Hebe :Dionysus :Aphrodite)

;Medios Hermanos (en general)
(with-db facts
    (doall 
      (run* [q]
        (medioHermano q :Ares))))
;(:Athena :Artemis :Hermes :Heracles :Persephone :Apollo :Dionysus :Aphrodite)

;Esposos
(with-db facts
    (doall
        (distinct (run* [q]
            (esposo q :Rhea)))))

;Esposas
(with-db facts
    (doall
        (distinct (run* [q]
            (esposa q :Zeus)))))
;(:Leto :Maia :Hera :Alcmene :Demeter :Semele :Dione)

;Sobrinos
(with-db facts
    (doall
        (distinct (run* [q]
            (sobrino q :Cronus)))))
;(:Epimetheus :Pleione :Zeus :Leto :Atlas :Hera :Demeter :Hades :Poseidon :Hestia)

;Tíos
(with-db facts
    (doall
        (distinct (run* [q]
            (tio q :Ares)))))
;(:Zeus :Hera :Demeter :Hades :Hestia :Poseidon)

;Tíos Segundos
(with-db facts
    (doall
        (distinct (run* [q]
            (tioSegundo q :Heracles)))))
;(:Epimetheus :Pleione :Leto :Atlas :Hera :Demeter :Hades :Poseidon :Hestia)

;Tíos Abuelos
(with-db facts
    (doall
        (distinct (run* [q]
            (tioAbuelo q :Athena)))))
;(:Oceanus :Rhea :Iapetus :Coeus :Phoebe :Tethys :Cronus)

;Cuñados
(with-db facts
    (doall
        (run* [q]
            (cunado q :Alcmene))))
;(:Hera :Demeter :Hades :Hestia :Poseidon)

;Suegro (Hombre)
(with-db facts
    (doall
        (run* [q]
            (suegro q :Alcmene))))
;(:Cronus)

;Suegra
(with-db facts
    (doall
        (run* [q]
            (suegra q :Atlas))))
;(:Tethys)

;Suegros (en general)
(with-db facts
    (doall
        (run* [q]
            (suegros q :Atlas))))
;(:Oceanus :Tethys)

;Nueras
(with-db facts
    (doall 
        (distinct (run* [q]
            (nuera q :Maia)))))
;(:Aphrodite)

;Yernos
(with-db facts
    (doall
        (distinct (run* [q]
            (yerno q :Dione)))))

;(:Hermes)




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

