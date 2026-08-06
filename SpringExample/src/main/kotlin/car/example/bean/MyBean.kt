package car.example.bean

class MyBean {
    var message: String? = null

    fun showMessage() {
        println("Message: $message")
    }

    override fun toString(): String {
        return "MyBean(message='$message')"
    }
}
