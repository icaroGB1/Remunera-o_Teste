package exceptions;

import java.sql.SQLException;

public class VendaNaoPermitidaException extends Exception {

	public VendaNaoPermitidaException(String msg, SQLException e) {
		super(msg);
	}

}
