;http://www.slideshare.net/normanrichards/corelogic-introduction

(run* [q]
  (fresh [smurf]
    (membero smurf [:papa :brainy :lazy :handy])
    (== q [smurf smurf])))

(run* [q]
  (fresh [smurf1 smurf2]
    (membero smurf1 [:papa :brainy :lazy :handy])
    (membero smurf2 [:papa :brainy :lazy :handy])
    (== q [smurf1 smurf2])))

(run* [q]
  (fresh [smurf1 smurf2 smurf3]
    (membero smurf1 [:papa :brainy :lazy :handy])
    (membero smurf2 [:papa :brainy :lazy :handy])
    (membero smurf3 [:papa :brainy :lazy :handy])
    (distincto [smurf1 smurf2 smurf3])
    (== q [smurf1 smurf2 smurf3])))

(run* [q]
  (fresh [smurf1 smurf2 smurf3]
    (== q [smurf1 smurf2 smurf3])
    (everyg #(membero % [:papa :brainy :lazy :handy]) q)
    (distincto q)))

(run* [q]
  (== q [(lvar) (lvar) (lvar)])
  (everyg #(membero % [:papa :brainy :lazy :handy]) q)
  (distincto q))

(run 1 [q]
  (fresh [tn ms al ga fl]
    (everyg #(membero % [:red :blue :green]) [tn ms al ga fl])
    (!= ms tn)
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
  (beatso :rock :paper))

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