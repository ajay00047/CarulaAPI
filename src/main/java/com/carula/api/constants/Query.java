package com.carula.api.constants;

public class Query {

	public static final String COUNT_USERS_MOBILE = "select count(1) from users where mobile=?";
	public static final String GET_USER_DETAILS = "select user_id,first_name,last_name,mobile,dp_path,pass_key from users where mobile=?";
	public static final String INSERT_USER = "insert into users(first_name,last_name,mobile) VALUES(?,?,?)";
	public static final String UPDATE_USER_OTP = "update users set otp=? where mobile=?";
	public static final String VERIFY_OTP = "select count(1) from users where otp=? and mobile=?";
	public static final String GET_USERID_FROM_PASSKEY = "select user_id from users where pass_key=?";
	public static final String UPDATE_PASS_KEY = "update users set pass_key=? where mobile=?";
	public static final String UPDATE_DP = "update users set dp_path=? where mobile=?";

	// car
	public static final String INSERT_CAR = "insert into cars(user_id,company,model,color,no) VALUES(?,?,?,?,?)";
	public static final String GET_CAR = "select car_id,user_id,company,model,color,no,created_date from cars where user_id=?";
	public static final String UPDATE_CAR = "update cars set company=?,model=?,color=?,no=? where user_id=?";

	// trips
	public static final String GET_TRIP_COUNT_BETWEEN_TIME = "select count(1) from owner_trips where user_id=? "
			+ "and start_date_time <= ? and drop_date_time >= ?";
	public static final String INSERT_TRIP = "insert into owner_trips (user_id, start_location, start_lang, start_long, drop_location, drop_lat, drop_long, duration, distance, fare, passengers, start_date_time, drop_date_time,remaining_passengers, polylines) "
			+ "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)";
	public static final String GET_TRIP_BETWEEN_TIME = "select trip_id, ot.user_id, start_location, start_lang, start_long, "
			+ "drop_location, drop_lat, drop_long, duration, distance, fare, passengers,remaining_passengers, "
			+ "start_date_time, drop_date_time, polylines, concat(us.first_name,' ',us.last_name) full_name,us.dp_path,us.mobile "
			+ "from owner_trips ot , users us where ot.user_id=us.user_id and ot.user_id!=? "
			+ "and start_date_time <= ? and drop_date_time >= ?";
}
