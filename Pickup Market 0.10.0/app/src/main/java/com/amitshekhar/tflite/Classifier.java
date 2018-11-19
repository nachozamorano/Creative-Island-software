package com.amitshekhar.tflite;
// librerias usadas en esta clase
import android.graphics.Bitmap;

import java.util.List;

public interface Classifier {

    class Recognition {
        /** Un identificador único para lo que se ha reconocido.         */
        private final String id;

        /**
         * Nombre para mostrar para el reconocimiento.
         */
        private final String title;

        /**
         Un puntaje ordenable de cuán bueno es el reconocimiento en relación con los demás. Más alto debería ser mejor.         */
        private final Float confidence;
//se asignan el id, titulo y el porcentaje
        public Recognition(
                final String id, final String title, final Float confidence) {
            this.id = id;
            this.title = title;
            this.confidence = confidence;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public Float getConfidence() {
            return confidence;
        }

        @Override
        public String toString() {
            String resultString = "";

            if (title != null) {
                resultString += title + " ";
            }

            if (confidence != null) {
                if(confidence>=0.5) {
                    resultString += String.format("(%.1f%%) ", confidence * 100.0f);
                }
                else{
                    resultString="--";
                }
            }

            return resultString.trim();
        }
    }


    List<Recognition> recognizeImage(Bitmap bitmap);

    void close();
}
