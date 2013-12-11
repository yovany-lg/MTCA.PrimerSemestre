(defn bell
    ([x]
        (bell x x))
    ([i j]
        (if (and (= i 1) (= j 1))
            1
            (if (and (> i 1) (= j 1))
                (recur (dec i) (dec i))
                (+ (bell (dec i) (dec j)) (bell i (dec j)))))))

(defn bellList
    ([x]
        (bellList (dec x) [[1]]))
    ([x vectOut]
        (if (= x 0)
            vectOut
            (recur (dec x) (conj vectOut (bellVect (peek vectOut)))))))

(defn bellVect
    [vect]
    (reduce (fn [x y] (conj x (+ y (peek x)))) [(peek vect)] vect))

(defn power
    [x n]
    (if (= n 0)
        1
        (* (power x (dec n)) x)))