(use 'clojure.core.logic)

(defrel father Father Son)
(defrel mother Mother Son)
(defrel brother Brother Sib)
(defrel male M)
(defrel female F)

(defn parent [p child]
  (conde
   ((father p child))
   ((mother p child))))

(defn brother [bro sib]
  (fresh [p]
         (parent p bro)
         (parent p sib)
         (male bro)
         (!= bro sib)))

(defn uncle [u person]
  (fresh [p]
         (brother u p)
         (parent p person)))

(facts father [['terach 'abraham]
               ['terach 'nachor]
               ['terach 'haran]
               ['abraham 'isaac]
               ['haran 'lot]
               ['haran 'milcah]
               ['haran 'yiscah]
               ['sarah 'isaac]])

(facts male (map list ['terach
                       'abraham
                       'nachor
                       'haran
                       'isaac
                       'lot
                       'sarah
                       'milcah
                       'yiscah]))

(run* [q]
  (parent q 'isaac))