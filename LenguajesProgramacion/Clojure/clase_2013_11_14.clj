(map str [1 2 3])

;Función Anónima
;1
(fn [x] (+ 1 x))
;2
#(+ 1 %1)

;Filter
(filter #(> %1 10)
    [5 11 9 10])

;Reduce
(reduce * [1 2 3 4])

;Función Mayor
(defn mayor
    ([]
        nil)
    ([n]
        n)
    ([n1 n2]
        (if (nil? n1)
            (if (nil? n2)
                nil
                n2)
            (if (nil? n2)
                n1
                (if (> n1 n2)
                    n1
                    n2)))))

(reduce mayor [5 8 10])

(loop [result [] x 5]
    (if (zero? x)
        result
        (recur (conj result x) (dec x))))
