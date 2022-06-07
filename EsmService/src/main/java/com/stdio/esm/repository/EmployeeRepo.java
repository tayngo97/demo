package com.stdio.esm.repository;

import com.stdio.esm.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 * @author AnhKhoa
 * @since 19/05/2022 - 11:11
 */
@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
}
