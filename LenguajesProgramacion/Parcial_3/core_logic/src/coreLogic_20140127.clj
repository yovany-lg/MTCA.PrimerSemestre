;http://www.slideshare.net/normanrichards/corelogic-introduction

(run* [q]
  (fresh [smurf]
    (membero smurf [:papa :brainy :lazy :handy]) ;Se considera a smurf como miembro de la lista
    (== q [smurf smurf])))  ;Unificación de la variable q con una lista que contiene a smurf
;Profuce cada instancia exitosa de las restricciones, 
;es decir todas las veces en que smurf es un elemento de la lista
;([:papa :papa][:brainy :brainy][:lazy :lazy][:handy :handy])

(run* [q]
  (fresh [smurf1 smurf2]
    (membero smurf1 [:papa :brainy :lazy :handy])
    (membero smurf2 [:papa :brainy :lazy :handy]); Ahora se consideran dos variables distintas asociadas a una lista
    (== q [smurf1 smurf2])  ;Ahora smurf1 y smurf2 son elementos distintos
    (distincto q))); Pero solo se desean las instancias en que tienen asociados elementos distintos de la lista

(run* [q]
  (fresh [smurf1 smurf2 smurf3]
    (membero smurf1 [:papa :brainy :lazy :handy])
    (membero smurf2 [:papa :brainy :lazy :handy])
    (membero smurf3 [:papa :brainy :lazy :handy])
    (distincto [smurf1 smurf2 smurf3])  ;De igual forma para tres elementos distintos
    (== q [smurf1 smurf2 smurf3])))


(run* [q]
  (fresh [smurf1 smurf2 smurf3]
    (== q [smurf1 smurf2 smurf3])
    (everyg #(membero % [:papa :brainy :lazy :handy]) q)  ;Para asociar cada elemento de q (que es una lista) a un elemento de la lista
    (distincto q)))

(run* [q]
  (== q [(lvar) (lvar)])
  (everyg #(membero % [:papa :brainy :lazy :handy :handy]) q)
  (distincto q))

(run 1 [q]  ;Ejemplo para determinar los colores de estados en un mapa geográfico
  (fresh [tn ms al ga fl]
    (everyg #(membero % [:red :blue :green]) [tn ms al ga fl]) ;Cada elemento de la lista (de variables) se le debe asignar un solo color
    (!= ms tn)  ;Restricciones sobre la lista de estados
    (!= ms al)
    (!= al tn)
    (!= al ga)
    (!= al fl)
    (!= ga fl)
    (!= ga tn)
    (== q {:tennesse tn
      :mississipi ms
      :alabama al
      :georgia ga
      :florida fl})))

(defn beatso [player1 player2]
  (conde 
    [(== player1 :rock) (== player2 :scissors)]
    [(== player1 :scissors) (== player2 :paper)]
    [(== player1 :paper) (== player2 :rock)]))

(run* [q]
  (beatso :rock :scissors))

(run* [q]
  (beatso :rock q))

(run* [q]
  (fresh [x y]
    (beatso x y)
    (== q [x y])))

(defrel rpsls gesture)

(fact rpsls :rock)
(fact rpsls :paper)
(fact rpsls :scissors)
(fact rpsls :lizard)
(fact rpsls :spock)

(run* [q]
  (rpsls q))

(defrel beats gesture1 gesture2)

(fact beats :scissors :paper)
(fact beats :paper :rock)
(fact beats :rock :lizard)
(fact beats :lizard :spock)
(fact beats :spock :scissors)
(fact beats :scissors :lizard)
(fact beats :lizard :paper)
(fact beats :paper :spock)
(fact beats :spock :rock)
(fact beats :rock :scissors)

(run* [q]
  (fresh [x y]
    (beats :spock x)
    (beats x y)
    (beats y :spock)
    (== q [:spock x y :spock])))


(defn pigso [q]
  (fresh [h1 h2 h3 t1 t2 t3]
    (== q [[:petey h1 t1][:pippin h2 t2][:petunia h3 t3]])
    (permuteo [t1 t2 t3] [:chocolate :popcorn :apple])
    (permuteo [h1 h2 h3] [:wood :straw :brick])
    (fresh [notpopcorn _]
      (membero notpopcorn [:chocolate :apple])
      (membero [:petey _ notpopcorn] q))
    (fresh [notwood _]
      (membero notwood [:straw :brick])
      (membero [:pippin notwood _] q))
    (fresh [_]
      (membero [_ :straw :popcorn] q))
    (fresh [_]
      (membero [:petunia _ :apple] q))
    (fresh [notbrick _]
      (membero notbrick [:straw :wood])
      (membero [_ notbrick :chocolate] q))))

(run* [q] (pigso q))