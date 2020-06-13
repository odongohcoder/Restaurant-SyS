package org.thekiddos.manager.models;

import com.sun.istack.Nullable;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class PayCheck {
    @NonNull
    private LocalDate payDate;
    @NonNull
    private double amount;
    @NonNull
    private String details;
}
