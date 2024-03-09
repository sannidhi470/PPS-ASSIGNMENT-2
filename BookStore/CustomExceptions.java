package BookStore;

//public class CustomExceptions {
//
//}
class TooManyFieldsException extends Exception {
    public TooManyFieldsException(String message) {
        super(message);
    }
}

class TooFewFieldsException extends Exception {
    public TooFewFieldsException(String message) {
        super(message);
    }
}

class MissingFieldException extends Exception {
    public MissingFieldException(String message) {
        super(message);
    }
}

class UnknownGenreException extends Exception {
    public UnknownGenreException(String message) {
        super(message);
    }
}
    class BadIsbn10Exception extends Exception {
        public BadIsbn10Exception(String message) {
            super(message);
        }
    }

    class BadIsbn13Exception extends Exception {
        public BadIsbn13Exception(String message) {
            super(message);
        }
    }

    class BadPriceException extends Exception {
        public BadPriceException(String message) {
            super(message);
        }
    }

    class BadYearException extends Exception {
        public BadYearException(String message) {
            super(message);
        }
    }
