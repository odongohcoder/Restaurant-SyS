package org.thekiddos.manager.repositories;

import org.thekiddos.manager.models.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private static Map<Long, Employee> employees = new HashMap<>();
    private static Map<Long, Customer> customers = new HashMap<>();
    private static Map<Long, Table> tables = new HashMap<>();
    private static Map<Long, Item> items = new HashMap<>();
    private static Map<Long, List<Reservation>> reservations = new HashMap<>();

    public static Employee getEmployeeById( Long employeeId ) {
        return employees.get( employeeId );
    }

    public static void addEmployee( Employee employee ) {
        employees.put( employee.getId(), employee );
    }

    public static void removeEmployeeById( Long empId ) {
        employees.remove( empId );
    }

    public static List<Employee> getEmployees() {
        return new ArrayList<>( employees.values() );
    }

    public static void addCustomer( Customer customer ) {
        customers.put( customer.getId(), customer );
    }

    public static Customer getCustomerById( Long customerId ) {
        return customers.get( customerId );
    }

    public static void removeCustomerById( Long customerId ) {
        customers.remove( customerId );
    }

    public static void addTable( Table table ) {
        tables.put( table.getId(), table );
    }

    public static Table getTableById( Long tableId ) {
        return tables.get( tableId );
    }

    public static void removeTableById( Long tableId ) {
        tables.remove( tableId );
    }

    public static void addReservation( Reservation reservation ) {
        List<Reservation> tableReservations = reservations.get( reservation.getTableId() );
        if ( tableReservations == null )
            tableReservations = new ArrayList<>();

        tableReservations.add( reservation );

        reservations.put( reservation.getTableId(), tableReservations );
    }

    public static List<Reservation> getReservationsByTableId( Long tableId ) {
        return reservations.computeIfAbsent( tableId, k -> new ArrayList<>() );
    }

    public static List<Reservation> getReservationsByCustomerId( Long customerId ) {
        List<Reservation> customerReservations = new ArrayList<>();
        for ( List<Reservation> tableReservations : reservations.values() )
            for ( Reservation reservation : tableReservations )
                if ( reservation.getCustomerId().equals( customerId ) )
                    customerReservations.add( reservation );
        return customerReservations;
    }

    public static void deleteReservation( Long tableId, LocalDate reservationDate ) {
        List<Reservation> tableReservation = Database.getReservationsByTableId( tableId );
        for ( int i = 0; i < tableReservation.size(); ++i )
            if ( tableReservation.get( i ).getDate().equals( reservationDate ) ) {
                tableReservation.remove( i );
                return;
            }
    }

    public static Item getItemById( Long itemId ) {
        return items.get( itemId );
    }

    public static void addItem( Item item ) {
        items.put( item.getId(), item );
    }

    public static void removeItemById( Long itemId ) {
        items.remove( itemId );
    }

    public static Reservation getCurrentReservationByTableId( Long tableId ) {
        List<Reservation> tableReservation = Database.getReservationsByTableId( tableId );
        LocalDate now = LocalDate.now();
        for ( Reservation reservation : tableReservation ) {
            if ( reservation.getDate().equals( now ) )
                return reservation;
        }
        return null;
    }

    public static void init() {
        employees.clear();
        customers.clear();
        tables.clear();
        items.clear();
        reservations.clear();
    }
}
