package com.carula.api.constants;

public enum ErrorCodes {

    CODE_200 {
        public String getErrorMessage() {
            return "OK";
        }
    },
    
    CODE_201 {
        public String getErrorMessage() {
            return "Data Found";
        }
    },
    
    CODE_202 {
        public String getErrorMessage() {
            return "No Data Found";
        }
    },
	CODE_007 {
		public String getErrorMessage() {
			return "Data saved successfuly";
		}
	},
	
	CODE_400 {
		public String getErrorMessage() {
			return "Data not saved";
		}
	},
	
	CODE_700 {
		public String getErrorMessage() {
			return "Mobile Already Present. Please Relogin";
		}
	},
	
	CODE_701 {
		public String getErrorMessage() {
			return "Mobile Not Present. Please SignUp";
		}
	},
	
	CODE_702 {
		public String getErrorMessage() {
			return "SignUp Successful! OTP sent to your mobile number.";
		}
	},
	
	CODE_703 {
		public String getErrorMessage() {
			return "OTP sent to your mobile number.";
		}
	},
	
	CODE_704 {
		public String getErrorMessage() {
			return "OTP verified successfully.";
		}
	},
	
	CODE_705 {
		public String getErrorMessage() {
			return "Wrong OTP! Please try again.";
		}
	},
	
	CODE_707 {
		public String getErrorMessage() {
			return "Login Expired! Please relogin.";
		}
	},
	
	CODE_708 {
		public String getErrorMessage() {
			return "Your trip time overlaps with another trip!. Please select another timings.";
		}
	}
	;

	public String getErrorMessage() {
		return "Error Message";
	}

}
