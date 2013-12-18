
;Procesamiento de URLs

;Entradas auxiliares
(def line "<a href=\"http://getfirefox.com\">gecko</a>")
(def line2 "<img alt=\"Cd_logo\" class=\"cd_logo\" height=\"33\" src=\"/images/cd_logo.png?1317195880\" width=\"52\">")
(def line3 "ClojureDocs uses some elements of HTML5 / CSS3, and is best viewed in an up-to-date <a href=\"http://getfirefox.com/\">gecko</a> / <a href=\"http://www.google.com/chrome\">webkit</a> -based browser.")

(use 'clojure.java.io)

(defn getLink
    "Obtiene la dirección de un enlace"
    [entry]
    (get (re-find #"[h|H][r|R][e|E][f|F]=\p{Punct}([\w\p{Punct}]+)\p{Punct}" entry) 1))

(defn getLinkInfo
    "Obtiene la información de un enlace"
    [line]
    (let [regexVect (re-find #"\<[a|A]\s([\w[\p{Punct}&&[^\>]]\s]+)\>([\w[\p{Punct}&&[^\>]]\s]+)\<\/[a|A]\>" line)]
        (if (empty? regexVect)
            {}
            (hash-map :links (hash-map :title (get regexVect 2)
                                :link (getLink (get regexVect 1)))))))

(defn getImageSrc
    "Obtiene la dirección de una imagen"
    [entry]
    (get (re-find #"[s|S][r|R][c|C]=\p{Punct}([\w\p{Punct}]+)\p{Punct}" entry) 1))

(defn getImageAlt
    "Obtiene el parámetro alt en una entrada de imagen"
    [entry]
    (get (re-find #"[a|A][l|L][t|T]=\p{Punct}([\w\p{Punct}]+)\p{Punct}" entry) 1))

(defn getImageInfo
    "Obtiene la información de una imagen"
    [line]
    (let [regexVect (re-find #"\<[I|i][m|M][g|G] ([\w[\p{Punct}&&[^\>]]\s]+)\>" line)]
        (if (empty? regexVect)
            {}
            (hash-map :images (hash-map :source (getImageSrc (get regexVect 1))
                                        :alt (getImageAlt (get regexVect 1)))))))

(defn webLineToMap
    "Obtiene la información de una línea de entrada en un hash-map"
    [line]
    (let [linkMap (merge (getLinkInfo line) (getImageInfo line)) ]
        (if (empty? linkMap)
            {}
            linkMap)))

(defn webRead
    "Realiza la lectura y procesamiento de una URL"
    [webAdd]
    (with-open [rdr (reader webAdd)]
        (reduce 
            (fn [webHash line]
                (merge-with conj webHash (webLineToMap line)))
            {:links [] :images []}
            (line-seq rdr))))



;Ejemplos de uso
(webRead "http://clojuredocs.org/clojure_core")

(with-open [rdr (clojure.java.io/reader "http://www.google.com")]
         (printf "%s\n" (clojure.string/join "\n" (line-seq rdr))))

(with-open [rdr (clojure.java.io/reader "http://clojuredocs.org/clojure_core")]
    (doseq [line (line-seq rdr)]
        (println (str line "\n..."))))

