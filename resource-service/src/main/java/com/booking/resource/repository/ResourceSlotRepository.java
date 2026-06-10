package com.booking.resource.repository;

import com.booking.resource.entity.ResourceSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceSlotRepository extends JpaRepository<ResourceSlot, Long> {

    Optional<ResourceSlot> findByBookingId(Long bookingId);

    /**
     * Удаляем слот при отмене бронирования.
     * @Modifying + @Transactional (в сервисе) — для DML-запросов через JPQL.
     */
    @Modifying
    @Query("DELETE FROM ResourceSlot s WHERE s.bookingId = :bookingId")
    int deleteByBookingId(@Param("bookingId") Long bookingId);

    boolean existsByBookingId(Long bookingId);
}