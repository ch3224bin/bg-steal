(ns bg-steal.core
  (:require [clojure.java.io :as io])
  (:gen-class))

(def source-dir "/mnt/c/Users/rockmonkey/AppData/Local/Packages/Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy/LocalState/Assets/")
(def dest-dir "/mnt/c/Users/rockmonkey/Pictures/바탕화면/")

(defn in?
    "true if coll contains elm"
    [elm coll]
    (some #(= elm %) coll))

(defn not-in?
    "false if coll contains elm"
    [elm coll]
    (not (in? elm coll)))

(def stolen-files (map #(.getName %)
                (filter #(.isFile %) (file-seq (io/file dest-dir)))))

(defn is-wide [f]
    (#(> (.getWidth %) (.getHeight %)) (javax.imageio.ImageIO/read f)))

(defn copy-file [dest-path coll]
    (doseq [y coll] (io/copy y (io/file (str dest-path (.getName y) ".jpg")))))

(defn -main [& args]
  (copy-file dest-dir
             (filter #(is-wide %)
                     (filter #(not-in? (str (.getName %) ".jpg") stolen-files)
                             (filter #(.isFile %)
                                     (file-seq (io/file source-dir)))))))
