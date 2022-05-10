package ejemplo.servidorchat

import java.util.*

class MensajesChat : Observable() {
    // Indica que el mensaje ha cambiado

    // Notifica a los observadores que el mensaje ha cambiado y se lo pasa
    // (Internamente notifyObservers llama al método update del observador)
    var mensaje: String? = null
        set(mensaje) {
            field = mensaje

            // Indica que el mensaje ha cambiado
            setChanged()

            // Notifica a los observadores que el mensaje ha cambiado y se lo pasa
            // (Internamente notifyObservers llama al método update del observador)
            this.notifyObservers(this.mensaje)
        }
}