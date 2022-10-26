package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INTEREST_NETFLIX;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.ALICE;
import static seedu.address.testutil.TypicalStudents.getTypicalMassLinkers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.student.Mod;
import seedu.address.model.student.Student;
import seedu.address.model.student.exceptions.DuplicateStudentException;
import seedu.address.testutil.StudentBuilder;

public class MassLinkersTest {

    private final MassLinkers massLinkers = new MassLinkers();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), massLinkers.getStudentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> massLinkers.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMassLinkers_replacesData() {
        MassLinkers newData = getTypicalMassLinkers();
        massLinkers.resetData(newData);
        assertEquals(newData, massLinkers);
    }

    @Test
    public void resetData_withDuplicateStudents_throwsDuplicateStudentException() {
        // Two students with the same identity fields
        Student editedAlice = new StudentBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB)
                .withInterests(VALID_INTEREST_NETFLIX)
                .build();
        List<Student> newStudents = Arrays.asList(ALICE, editedAlice);
        MassLinkersStub newData = new MassLinkersStub(newStudents);

        assertThrows(DuplicateStudentException.class, () -> massLinkers.resetData(newData));
    }

    @Test
    public void hasStudent_nullStudent_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> massLinkers.hasStudent(null));
    }

    @Test
    public void hasStudent_studentNotInMassLinkers_returnsFalse() {
        assertFalse(massLinkers.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentInMassLinkers_returnsTrue() {
        massLinkers.addStudent(ALICE);
        assertTrue(massLinkers.hasStudent(ALICE));
    }

    @Test
    public void hasStudent_studentWithSameIdentityFieldsInMassLinkers_returnsTrue() {
        massLinkers.addStudent(ALICE);
        Student editedAlice = new StudentBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB)
                .withInterests(VALID_INTEREST_NETFLIX)
                .build();
        assertTrue(massLinkers.hasStudent(editedAlice));
    }

    @Test
    public void getStudentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> massLinkers.getStudentList().remove(0));
    }

    /**
     * A stub ReadOnlyMassLinkers whose students list can violate interface constraints.
     */
    private static class MassLinkersStub implements ReadOnlyMassLinkers {
        private final ObservableList<Student> students = FXCollections.observableArrayList();
        private final ObservableList<Mod> mods = FXCollections.observableArrayList();

        MassLinkersStub(Collection<Student> students) {
            this.students.setAll(students);
        }

        @Override
        public ObservableList<Student> getStudentList() {
            return students;
        }
    }

}
