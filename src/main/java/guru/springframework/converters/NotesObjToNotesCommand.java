package guru.springframework.converters;

import guru.springframework.command_objs.NotesCommand;
import guru.springframework.domain.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesObjToNotesCommand implements Converter<Notes, NotesCommand> {

    @Nullable
    @Synchronized
    @Override
    public NotesCommand convert(Notes notes) {
        if(notes == null) return null;

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(notes.getId());
        notesCommand.setRecipe(notes.getRecipe());
        notesCommand.setRecipeNotes(notes.getRecipeNotes());

        return notesCommand;
    }
}
