package explore_with_me.main_server.compilation.service;

import explore_with_me.main_server.compilation.CompilationRepository;
import explore_with_me.main_server.compilation.model.Compilation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import explore_with_me.main_server.compilation.model.CompilationAdminDto;
import explore_with_me.main_server.compilation.model.CompilationDto;
import explore_with_me.main_server.compilation.model.CompilationMapper;
import explore_with_me.main_server.event.EventRepository;
import explore_with_me.main_server.event.model.Event;
import explore_with_me.main_server.exception.NotFoundException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

/**
 * Compilation service
 */

@Service
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    /**
     * Create compilation
     * @param compilationDto the compilation dto
     * @return the compilation
     */

    @Override
    public Compilation create(CompilationDto compilationDto) {
        Compilation compilation = CompilationMapper.toCompilation(compilationDto);
        List<Event> events = eventRepository.findAllByIdIn(compilationDto.getEvents());
        compilation.setEvents(events);
        return compilationRepository.save(compilation);
    }

    /**
     * Delete compilation
     * @param compId the compilation id
     */

    @Override
    public void delete(Long compId) {
        compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + compId + " not found"));
        compilationRepository.deleteById(compId);
    }

    /**
     * Update compilation
     * @param compId              the compilation id
     * @param compilationAdminDto the compilation admin dto
     * @return the compilation
     */

    @Override
    public Compilation update(Long compId, CompilationAdminDto compilationAdminDto) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + compId + " not found"));
        if (compilationAdminDto.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(compilationAdminDto.getEvents());
            compilation.setEvents(events);
        }
        if (compilationAdminDto.getTitle() != null) {
            compilation.setTitle(compilationAdminDto.getTitle());
        }
        if (compilationAdminDto.getPinned() != null) {
            compilation.setPinned(compilationAdminDto.getPinned());
        }
        return compilationRepository.save(compilation);
    }

    /**
     * Find all collection
     * @param pinned   the pinned
     * @param pageable the pageable
     * @return the collection
     */

    @Override
    public Collection<Compilation> findAll(Boolean pinned, Pageable pageable) {
        if (pinned != null) {
            return compilationRepository.findAllByPinned(pinned, pageable).getContent();
        } else {
            return compilationRepository.findAll(pageable).getContent();
        }
    }

    /**
     * Find compilation by id
     * @param compId the compilation id
     * @return the compilation
     */

    @Override
    public Compilation findById(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + compId + " not found"));
    }
}