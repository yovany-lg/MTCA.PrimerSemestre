;http://www.slideshare.net/normanrichards/corelogic-introduction

(run* [q]
  (fresh [smurf]
    (membero smurf [:papa :brainy :lazy :handy]) ;Se considera a smurf como miembro de la lista
    (== q [smurf smurf])))  ;Unificación de la variable q con una lista que contiene a smurf
;Produce cada instancia exitosa de las restricciones, 
;es decir todas las veces en que smurf es un elemento de la lista
;([:papa :papa] [:brainy :brainy] [:lazy :lazy] [:handy :handy])

(run* [q]
  (fresh [smurf1 smurf2]
    (membero smurf1 [:papa :brainy :lazy :handy])
    (membero smurf2 [:papa :brainy :lazy :handy]); Ahora se consideran dos variables distintas asociadas a una lista
    (== q [smurf1 smurf2])  ;Ahora smurf1 y smurf2 son elementos distintos
    (distincto q))); Pero solo se desean las instancias en que tienen asociados elementos distintos de la lista
;([:papa :brainy] [:brainy :papa] [:papa :lazy] [:papa :handy] [:lazy :papa] [:brainy :lazy] [:brainy :handy] [:lazy :brainy] [:handy :papa] [:lazy :handy] [:handy :brainy] [:handy :lazy])

(run* [q]
  (fresh [smurf1 smurf2 smurf3]
    (membero smurf1 [:papa :brainy :lazy :handy])
    (membero smurf2 [:papa :brainy :lazy :handy])
    (membero smurf3 [:papa :brainy :lazy :handy])
    (distincto [smurf1 smurf2 smurf3])  ;De igual forma para tres elementos distintos
    (== q [smurf1 smurf2 smurf3])))
;([:papa :brainy :lazy] [:papa :brainy :handy] [:brainy :papa :lazy] [:brainy :papa :handy] [:papa :lazy :brainy] [:papa :lazy :handy] [:papa :handy :brainy] [:papa :handy :lazy] [:lazy :papa :brainy] [:brainy :lazy :papa] [:lazy :papa :handy] [:brainy :handy :papa] [:brainy :lazy :handy] [:brainy :handy :lazy] [:lazy :brainy :papa] [:lazy :brainy :handy] [:handy :papa :brainy] [:handy :papa :lazy] [:lazy :handy :papa] [:lazy :handy :brainy] [:handy :brainy :papa] [:handy :brainy :lazy] [:handy :lazy :papa] [:handy :lazy :brainy])

(run* [q]
  (fresh [smurf1 smurf2 smurf3]
    (== q [smurf1 smurf2 smurf3])
    (everyg #(membero % [:papa :brainy :lazy :handy]) q)  ;Para asociar cada elemento de q (que es una lista de variables lógicas) a un elemento de la lista
    (distincto q)))
;Produciendo un resultado similar al anterior
;([:papa :brainy :lazy] [:brainy :papa :lazy] [:papa :brainy :handy] [:papa :lazy :brainy] [:brainy :papa :handy] [:lazy :papa :brainy] [:papa :handy :brainy] [:papa :lazy :handy] [:papa :handy :lazy] [:brainy :lazy :papa] [:lazy :brainy :papa] [:handy :papa :brainy] [:handy :brainy :papa] [:brainy :handy :papa] [:handy :papa :lazy] [:lazy :papa :handy] [:brainy :lazy :handy] [:brainy :handy :lazy] [:handy :lazy :papa] [:handy :brainy :lazy] [:lazy :brainy :handy] [:lazy :handy :papa] [:handy :lazy :brainy] [:lazy :handy :brainy])

(run* [q]
  (== q [(lvar) (lvar)])
  (everyg #(membero % [:papa :brainy :lazy :handy :handy]) q)
  (distincto q))
;([:papa :brainy] [:brainy :papa] [:papa :lazy] [:papa :handy] [:papa :handy] [:lazy :papa] [:brainy :lazy] [:brainy :handy] [:lazy :brainy] [:brainy :handy] [:handy :papa] [:lazy :handy] [:handy :brainy] [:lazy :handy] [:handy :papa] [:handy :lazy] [:handy :brainy] [:handy :lazy])

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
    (== q {:tennesse tn ;La asociación de colores en las variables, se ve reflejada en un hashMap
      :mississipi ms
      :alabama al
      :georgia ga
      :florida fl})))
;({:tennesse :blue, :mississipi :red, :alabama :green, :georgia :red, :florida :blue})

(defn beatso [player1 player2]
  "Función para el juego de piedra papel o tijeras"
  (conde 
    [(== player1 :rock) (== player2 :scissors)]
    [(== player1 :scissors) (== player2 :paper)]
    [(== player1 :paper) (== player2 :rock)]))

(run* [q]
  (beatso :rock :scissors))

(run* [q]
  (beatso :rock q)) ;enemigo que rock puede vencer
;(:scissors)

(run* [q]
  (fresh [x y]
    (beatso x y)
    (== q [x y])))  ;Una secuencia de tres movimientos en donde el elemento vencido en la juaga anterior vence en la siguiente jugada
;([:rock :scissors] [:scissors :paper] [:paper :rock])

;Ahora un juego de piedra papel o tijeras mas completo
(defrel rpsls gesture)  ;Pero se hace uso de relafiones

;Old version < 0.8.5
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


(defn pigso [q] ;Problema de los puerquitos, algo parecido al problema de el hombre de la cebra
  (fresh [h1 h2 h3 t1 t2 t3]
    (== q [[:petey h1 t1][:pippin h2 t2][:petunia h3 t3]])
    (permuteo [t1 t2 t3] [:chocolate :popcorn :apple])  
    (permuteo [h1 h2 h3] [:wood :straw :brick]) ;La casa de los puerquitos
    (fresh [notpopcorn _] ;Se definen las restricciones según el problema dado
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

(run* [q] (pigso q))  ; Através de las restricciones dadas se debe obtener la solución al problema
;([[:petey :wood :chocolate] [:pippin :straw :popcorn] [:petunia :brick :apple]])