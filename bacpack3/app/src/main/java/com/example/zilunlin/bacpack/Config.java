package com.example.zilunlin.bacpack;

/**
 * Created by Zilun Lin on 9/1/2017.
 */

public class Config {

    //Reference to our PHP scripts

    //User table
    public static final String URL_USER_ADD = "http://192.168.0.103/android/CRUD/user/addUser.php";
    public static final String URL_USER_LOGIN = "http://192.168.0.103/android/CRUD/user/logInUser.php?id=";
    public static final String URL_USER_lIST_EVENTS = "http://192.168.0.103/android/CRUD/user/getUserEvents.php?id=";
    public static final String URL_USER_JOIN_EVENT = "http://192.168.0.103/android/CRUD/user/participantUser.php";
    /*multiple get*/ public static final String URL_USER_QUIT_EVENT = "http://192.168.0.103/android/CRUD/user/quitEventUser.php?id=&event_id=";

    //Event table, should have clarified better, but id in this context = event_id
    public static final String URL_ADD_EVENT = "http://192.168.0.103/android/CRUD/event/addEvent.php";
    public static final String URL_APPROVE_EVENT = "http://192.168.0.103/android/CRUD/event/approveEvent.php";
    public static final String URL_DELETE_EVENT = "http://192.168.0.103/android/CRUD/event/deleteEvent.php?id=";
    public static final String URL_GET_ALL_EVENT = "http://192.168.0.103/android/CRUD/event/getAllEvent.php?user_id=";
    public static final String URL_GET_UNAPPROVED_EVENT = "http://192.168.0.103/android/CRUD/event/getAllUnapprovedEvent.php?";
    public static final String URL_GET_SPECIFIC_EVENT = "http://192.168.0.103/android/CRUD/event/getEventInfo.php?event_id=";
    public static final String URL_GET_MESSAGE = "http://192.168.0.103/android/CRUD/event/getMessageInfo.php?event_id";
    public static final String URL_UPDATE_EVENT = "http://192.168.0.103/android/CRUD/event/updateEvent.php";

    public static final String URL_IS_USER_PARTICIPATING = "https://192.168.0.103/android/CRUD/user/checkParticipatingEvents.php";
    //=================================================================//

    //POST Request holders
    //event
    public static final String KEY_EVENT_NAME = "event_name";
    public static final String KEY_EVENT_DESCRIPTION = "description";
    public static final String KEY_EVENT_PRICE = "price";
    public static final String KEY_EVENT_MEDIA = "media";
    public static final String KEY_EVENT_ORGANIZER = "organizer_id";
    public static final String KEY_EVENT_VACANCY = "vacancy";
    public static final String KEY_EVENT_LOCATION = "location";
    public static final String KEY_EVENT_TYPE = "event_type";
    public static final String KEY_EVENT_START_DT = "start_dt";
    public static final String KEY_EVENT_END_DATE = "end_date";
    public static final String KEY_EVENT_WEEKDAY = "weekday";
    public static final String KEY_EVENT_APPROVAL = "approval";

    //users
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_PASSWORD = "password";
    public static final String KEY_USER_PERMISSION = "permission";
    public static final String KEY_USER_TYPE = "user_type";
    public static final String KEY_USER_PARTICIPATION = "event_id";
    //user, teacher
    public static final String KEY_USER_DEPARTMENT = "department";
    //user, student
    public static final String KEY_USER_GPA = "gpa";
    public static final String KEY_USER_ATTENDANCE = "attendance";
    public static final String KEY_USER_TUTOR = "tutor_group";
    public static final String KEY_USER_INTERESTS = "subject_interests";
    //user, parents
    public static final String KEY_USER_PTA = "pta";

    //==============================================================//

    //Tags returned from JSON

    public static final String TAG_EVENT_ID = "event_id";
    public static final String TAG_EVENT_ID2 = "event_id2";
    public static final String TAG_EVENT_NAME = "name";
    public static final String TAG_EVENT_DESCRIPTION = "description";
    public static final String TAG_EVENT_PRICE = "price";
    public static final String TAG_EVENT_MEDIA = "media";
    public static final String TAG_EVENT_ORGANIZER = "organizer_id";
    public static final String TAG_EVENT_VACANCY = "vacancy";
    public static final String TAG_EVENT_LOCATION = "location";
    public static final String TAG_EVENT_TYPE = "event_type";
    public static final String TAG_EVENT_START_DT = "start_datetime";
    public static final String TAG_EVENT_END_DATE = "end_date";
    public static final String TAG_EVENT_WEEKDAY = "weekday";
    public static final String TAG_EVENT_APPROVAL = "approval";

    //users
    public static final String TAG_USER_ID = "id";
    public static final String TAG_USER_NAME = "name";
    public static final String TAG_USER_PASSWORD = "password";
    public static final String TAG_USER_PERMISSION = "permission";
    public static final String TAG_USER_TYPE = "user_type";
    public static final String TAG_USER_PARTICIPATION = "event_id";
    //user, teacher
    public static final String TAG_USER_DEPARTMENT = "department";
    //user, student
    public static final String TAG_USER_GPA = "gpa";
    public static final String TAG_USER_ATTENDANCE = "attendance";
    public static final String TAG_USER_TUTOR = "tutor_group";
    public static final String TAG_USER_INTERESTS = "subject_interests";
    //user, parents
    public static final String TAG_USER_PTA = "pta";

    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_USER_PARTICIPATINGEVENTS_ARRAY = "final_result";
    public static final String TAG_JSON_OUTPUT = "output";

    //==============================================================//

    //Variables to be passed from one activity to another
    //Values to be passed OrganizerID, User Permissions, EventID, UserID.
    public static final String ORGANIZER_ID = "org id";
    public static final String USER_PERMISSION = "permissions";
    public static final String EVENT_ID = "event id";
    public static final String USER_ID = "user id";

}
