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
	public static final String UPDATE_NAME = "update users set first_name=?,last_name=? where mobile=?";

	// car
	public static final String INSERT_CAR = "insert into cars(user_id,company,model,color,no) VALUES(?,?,?,?,?)";
	public static final String GET_CAR = "select car_id,user_id,company,model,color,no,created_date from cars where user_id=?";
	public static final String UPDATE_CAR = "update cars set company=?,model=?,color=?,no=? where user_id=?";

	// trips
	public static final String GET_TRIP_COUNT_BETWEEN_TIME = "select count(1) from owner_trips where user_id=? "
			+ "and start_date_time <= ? and drop_date_time >= ? and status != 'CAN' ";
	public static final String INSERT_TRIP = "insert into owner_trips (user_id, start_location, start_lang, start_long, "
			+ "drop_location, drop_lat, drop_long, duration, distance, fare, passengers, start_date_time, drop_date_time,"
			+ "remaining_passengers, polylines) " + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
	public static final String GET_TRIP_BETWEEN_TIME = "select trip_id, ot.user_id, start_location, start_lang, start_long, "
			+ " drop_location, drop_lat, drop_long, duration, distance, fare, passengers,remaining_passengers, "
			+ " start_date_time, drop_date_time, polylines, concat(us.first_name,' ',us.last_name) full_name,us.dp_path,us.mobile,"
			+ " cc.company,cc.model,cc.color,cc.no"
			+ " from owner_trips ot , users us, cars cc where us.user_id=cc.user_id and ot.user_id=us.user_id and ot.user_id!=? "
			+ " and start_date_time <= ? and drop_date_time >= ? and ot.status not in ('CAN') and ot.trip_id not in ("
			+ " select distinct trip_id from trip_requests tr where tr.req_user_id=? ) ";

	public static final String MY_TRIP_BY_ID_PASSENGER = "select ot.trip_id, ot.user_id, ot.start_location, ot.start_lang, "
			+ "ot.start_long, ot.drop_location, ot.drop_lat, ot.drop_long, ot.duration, ot.distance, ot.fare, ot.passengers,"
			+ "ot.remaining_passengers, ot.start_date_time,ot.drop_date_time,ot.polylines, "
			+ "concat(us.first_name,' ',us.last_name) full_name,us.dp_path,us.mobile,ot.status,"
			+ "tr.trip_request_id,tr.start_loc,tr.start_lat,tr.start_long,tr.drop_loc,tr.drop_lat,tr.drop_long,"
			+ "tr.walk_polyline_start,tr.walk_polyline_drop,tr.walk_dist_start,tr.walk_duration_start,"
			+ "tr.walk_dist_drop,tr.walk_duration_drop,tr.status,	tr.start_date_time, cc.company,cc.model,cc.color,cc.no from owner_trips ot , "
			+ "trip_requests tr, users us, cars cc where us.user_id=cc.user_id and ot.user_id=us.user_id and ot.trip_id=tr.trip_id and tr.req_user_id=? ";

	public static final String MY_TRIP_BY_ID_OWNER = "select trip_id, start_location, start_lang, start_long, "
			+ " drop_location, drop_lat, drop_long, duration, distance, fare, passengers,remaining_passengers, "
			+ " start_date_time, drop_date_time, polylines , status ,(select count(1) from trip_requests tr where status in ('PEN') and tr.trip_id=ot.trip_id ) as remaining_requests" 
			+ " from owner_trips ot where user_id=? ORDER BY start_date_time DESC";

	public static final String ALL_TRIP_REQUESTS_OWNER = "select tr.trip_id, tr.req_user_id, ot.start_location, ot.start_lang, "
			+ "ot.start_long, ot.drop_location, ot.drop_lat, ot.drop_long, ot.duration, ot.distance, ot.fare, ot.passengers,"
			+ "ot.remaining_passengers, ot.start_date_time,ot.drop_date_time,ot.polylines, "
			+ "concat(us.first_name,' ',us.last_name) full_name,us.dp_path,us.mobile,ot.status,"
			+ "tr.trip_request_id,tr.start_loc,tr.start_lat,tr.start_long,tr.drop_loc,tr.drop_lat,tr.drop_long,"
			+ "tr.walk_polyline_start,tr.walk_polyline_drop,tr.walk_dist_start,tr.walk_duration_start,"
			+ "tr.walk_dist_drop,tr.walk_duration_drop,tr.status,	tr.start_date_time from owner_trips ot , "
			+ "trip_requests tr, users us where tr.req_user_id=us.user_id and ot.trip_id=tr.trip_id and tr.status != 'REJ' "
			+ "and tr.status != 'CAN' and tr.trip_id=? ";

	public static final String PROC_REQUEST_TRIP = "{call proc_request_trip(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)}";

	public static final String UPDATE_TRIP_STATUS_OWNER = "update owner_trips set status=? where trip_id=? and user_id=? LIMIT 1";
	public static final String UPDATE_TRIP_STATUS_PASSENGER = "update trip_requests set status=? where trip_id=? and trip_request_id=? LIMIT 1";
	
	public static final String SELECT_REQUEST_STATUS = "select status from trip_requests where trip_request_id=?";
	public static final String REMAININ_PASSENGER_COUNT_WITH_LOCK = "select remaining_passengers from owner_trips where trip_id=? for update";
}
