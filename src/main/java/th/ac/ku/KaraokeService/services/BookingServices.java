package th.ac.ku.KaraokeService.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import th.ac.ku.KaraokeService.models.BookingModel;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.Calendar;
@Service
public class BookingServices {


    public String createBooking(BookingModel booking,String type)  {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        if(type.equals("book")){
            booking.setType("booking");
            booking.setStatus("Booked");
        }
        else if(type.equals("walkin")){
            booking.setType("walkin");
            booking.setStatus("Completed");
        }
        ApiFuture<WriteResult> addBooking = dbFireStore.collection("bookings").document(booking.getBookingID()).set(booking);
        if(addBooking.isCancelled()) return "Failed";
        else return "Success";
    }
    public String checkBookingTime(BookingModel booking) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        Calendar bookingStart = Calendar.getInstance();
        Calendar bookingEnd = Calendar.getInstance();
        bookingStart.setTime(booking.getStartTime());
        bookingEnd.setTime(booking.getEndTime());
        List<QueryDocumentSnapshot> bookingDocs= dbFireStore.collection("bookings")
                .whereEqualTo("roomId",booking.getRoomId()).get().get().getDocuments();
        for (DocumentSnapshot bookDoc:bookingDocs){
            Date start = bookDoc.getDate("startTime");
            Date end = bookDoc.getDate("endTime");
            Calendar startTime = Calendar.getInstance();
            Calendar endTime = Calendar.getInstance();
            assert start != null;
            assert end != null;
            startTime.setTime(start);
            endTime.setTime(end);

            if(bookingStart.equals(startTime) && bookingEnd.equals(endTime)){
                return "This time already in booking";
            } else if (bookingStart.after(startTime) && bookingStart.before(endTime)) {
                return "This time already in booking";
            } else if (bookingEnd.after(startTime) && bookingEnd.before(endTime)) {
                System.out.println(3);
                return "This time already in booking";
            } else if (bookingStart.before(startTime) && bookingEnd.equals(endTime)) {
                System.out.println(4);
                return "This time already in booking";
            } else if (bookingStart.equals(startTime) && bookingEnd.after(endTime)) {
                System.out.println(5);
                return "This time already in booking";
            } else if (bookingStart.before(startTime) && bookingEnd.after(endTime) ) {
                System.out.println(6);
                return "This time already in booking";
            }
        }
        return "Go to payment";
    }
    public boolean getRoomStatus(String roomId) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        Date dateTimeNow = new Date();
        List<QueryDocumentSnapshot> roomNow =  dbFireStore.collection("bookings")
                .whereEqualTo("roomId",roomId).get().get().getDocuments();
        for(DocumentSnapshot docRoom:roomNow){
            Date startTime = docRoom.getDate("startTime");
            Date endTime = docRoom.getDate("endTime");
            assert startTime != null;
            assert endTime != null;
            if(startTime.compareTo(dateTimeNow) < 0 && endTime.compareTo(dateTimeNow) >0 ){
                return true;
            }
        }
        return false;
    }
    //show Queue Page
    public List<BookingModel> getBookingByRoomId(String roomId) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        List<BookingModel> allBooking = new ArrayList<>();
        List<QueryDocumentSnapshot> bookingsDoc = dbFireStore.collection("bookings")
                .whereEqualTo("roomId",roomId).get().get().getDocuments();
        for(DocumentSnapshot bookingDoc : bookingsDoc){
            String status = bookingDoc.getString("status");
            assert status != null;
            if(!status.equals("History")){
                allBooking.add(bookingDoc.toObject(BookingModel.class));
            }
        }
        allBooking.sort(Comparator.comparing(BookingModel::getStartTime));
        return allBooking;
    }
    //Booking for User Page
    public List<BookingModel> getBookingForUser(String user,boolean isBooking) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        List<BookingModel> allBooking = new ArrayList<>();
        Query query = dbFireStore.collection("bookings").whereEqualTo("customerName",user);
        List<QueryDocumentSnapshot> bookingsDoc;
        if(isBooking){
            bookingsDoc = query.whereEqualTo("status","Booked").get().get().getDocuments();
        }
        else{
            bookingsDoc = query.whereEqualTo("status","Completed").get().get().getDocuments();
        }
        for(DocumentSnapshot bookingDoc:bookingsDoc){
            allBooking.add(bookingDoc.toObject(BookingModel.class));
        }
        return allBooking;
    }

    public String updateStatus() throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        Date dateTimeNow = new Date();
        List<QueryDocumentSnapshot> allDocs = dbFireStore.collection("bookings")
                .whereLessThan("endTime",dateTimeNow).get().get().getDocuments();
        for(DocumentSnapshot doc :allDocs){
            BookingModel booking = doc.toObject(BookingModel.class);
            assert booking != null;
            dbFireStore.collection("bookings").document(booking.getBookingID())
                    .update("status","History");
        }
        return "Update Success";
    }
    public List<BookingModel> getHistory(String user) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        List<BookingModel> bookingHistories = new ArrayList<>();
        List<QueryDocumentSnapshot> allHistory = dbFireStore.collection("bookings")
                .whereEqualTo("status","History").whereEqualTo("customerName",user).get().get().getDocuments();
        for(DocumentSnapshot hisDoc : allHistory){
            bookingHistories.add(hisDoc.toObject(BookingModel.class));
        }
        return bookingHistories;
    }

    public List<BookingModel> getBookingByBranch(String branchId,String query) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        List<BookingModel> bookingList = new ArrayList<>();
        List<QueryDocumentSnapshot> bookingsDoc;
        Query normalQuery = dbFireStore.collection("bookings")
                .whereEqualTo("branchId",branchId).whereEqualTo("status","Booked");
        bookingsDoc = normalQuery.get().get().getDocuments();;

        for(DocumentSnapshot bookingDoc : bookingsDoc){
            BookingModel book = bookingDoc.toObject(BookingModel.class);
            if(query.isEmpty()) bookingList.add(book);
            else {
                assert book != null;
                if(book.getCustomerName().toLowerCase().contains(query.toLowerCase())) bookingList.add(book);
                else if (book.getBookingID().toLowerCase().contains(query.toLowerCase())) bookingList.add(book);
            }
        }
        return bookingList;
    }
    public String updatePaidStatus(String bookingId,String empName)
            throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> resultUpdate = dbFireStore.collection("bookings")
                .document(bookingId).update("status","Completed");
        dbFireStore.collection("bookings").document(bookingId).update("responseEmp",empName);
        return "Update Success" + resultUpdate.get().getUpdateTime().toString();
    }

    public List<BookingModel> searchTasksBooking(String branchId,boolean isBooking,String query)
            throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        List<BookingModel> bookingList = new ArrayList<>();
        List<QueryDocumentSnapshot> bookingsDoc;
        Query normalQuery = dbFireStore.collection("bookings").whereEqualTo("branchId",branchId);
        if(isBooking){
            normalQuery = normalQuery.whereEqualTo("type","booking")
                    .whereEqualTo("status","Completed");
        }
        else {
            normalQuery = normalQuery.whereEqualTo("type","walkin")
                    .whereEqualTo("status","Completed");;
        }
        bookingsDoc = normalQuery.get().get().getDocuments();
        for(DocumentSnapshot doc:bookingsDoc){
            BookingModel book =  doc.toObject(BookingModel.class);
            if(query.isEmpty()) bookingList.add(book);
            else {
                assert book != null;
                if(book.getBookingID().toLowerCase().contains(query.toLowerCase())
                || book.getCustomerName().toLowerCase().contains(query.toLowerCase())
                || book.getResponseEmp().toLowerCase().contains(query.toLowerCase())){
                    bookingList.add(book);
                }
            }
        }
        return bookingList;
    }

    public String cancelBooking(String bookingId) throws ExecutionException, InterruptedException {
        Firestore dbFireStore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> resultDelete = dbFireStore.collection("bookings").document(bookingId).delete();
        return "Delete Success at" + resultDelete.get().getUpdateTime().toString();
    }
}
