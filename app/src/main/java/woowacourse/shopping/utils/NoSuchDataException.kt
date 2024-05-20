package woowacourse.shopping.utils

class NoSuchDataException(message: String = DEFAULT_MESSAGE) : Exception(message) {
    companion object {
        private const val DEFAULT_MESSAGE = "No such data found"
    }
}
