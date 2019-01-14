package guru.springframework.converters;

import guru.springframework.command_objs.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotesObj implements Converter<NotesCommand, Notes> {

    @Nullable
    @Synchronized
    @Override
    public Notes convert(NotesCommand notesCommand) {
        if(notesCommand == null) return null;

        Notes notes = new Notes();
        notes.setId(notesCommand.getId());
        notes.setRecipe(notesCommand.getRecipe());
        notes.setRecipeNotes(notesCommand.getRecipeNotes());

        return notes;
    }
}
