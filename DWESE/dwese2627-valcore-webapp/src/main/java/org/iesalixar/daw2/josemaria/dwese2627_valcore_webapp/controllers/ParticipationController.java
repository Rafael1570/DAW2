package org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.controllers;

import jakarta.validation.Valid;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationCreateDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationDetailDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.dtos.ParticipationUpdateDTO;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.exceptions.DuplicateResourceException;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.exceptions.ResourceNotFoundException;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.services.ParticipationService;
import org.iesalixar.daw2.josemaria.dwese2627_valcore_webapp.services.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

/**
 * Controlador que maneja las operaciones CRUD para la entidad `Participation`.
 * Utiliza `ParticipationService` para delegar la lógica de negocio
 * y `PlayerService` para cargar el desplegable de jugadores.
 */
@Controller
@RequestMapping("/participations")
public class ParticipationController {

    private static final Logger logger = LoggerFactory.getLogger(ParticipationController.class);

    /** En Valorant cada equipo tiene 5 jugadores: como mucho se pueden hacer 5 bajas por ronda. */
    private static final int MAX_KILLS_PER_ROUND = 8;

    @Autowired
    private MessageSource messageSource;

    // Servicio para las operaciones del CRUD de Participation
    @Autowired
    private ParticipationService participationService;

    // Servicio de Player (para cargar el desplegable de jugadores del formulario)
    @Autowired
    private PlayerService playerService;

    /**
     * Lista paginada de participaciones.
     *
     * @param pageable parámetros de paginación y ordenación (page, size, sort).
     * @param model    modelo para la vista
     * @param locale   configuración regional actual
     * @return plantilla Thymeleaf para el listado de participaciones.
     */
    @GetMapping
    public String listParticipations(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
            Model model,
            Locale locale) {

        logger.info("Listando participaciones page={}, size={}, sort={}",
                pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());

        // Valor por defecto para que la vista pueda pintar las cabeceras aunque falle la carga
        String sortParam = "id,asc";
        try {
            // Delegamos en la capa de servicio
            Page<ParticipationDTO> listParticipationsDTOs = participationService.list(pageable);

            logger.info("Se han cargado {} participaciones en la página {}.",
                    listParticipationsDTOs.getNumberOfElements(), listParticipationsDTOs.getNumber());

            model.addAttribute("page", listParticipationsDTOs);

            // Para mantener el sort actual en los enlaces de la vista (sort=campo,asc|desc)
            if (listParticipationsDTOs.getSort().isSorted()) {
                Sort.Order order = listParticipationsDTOs.getSort().iterator().next();
                sortParam = order.getProperty() + "," + order.getDirection().name().toLowerCase();
            }
        } catch (Exception e) {
            logger.error("Error al listar las participaciones: {}", e.getMessage(), e);
            String errorMessage = messageSource.getMessage("msg.participation-controller.list.error", null, locale);
            model.addAttribute("errorMessage", errorMessage);
        }
        model.addAttribute("sortParam", sortParam);
        return "views/participation/participation-list";
    }

    /**
     * Muestra el formulario para crear una nueva participación.
     *
     * @param model  Modelo para pasar datos a la vista.
     * @param locale Configuración regional actual.
     * @return plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/new")
    public String showNewForm(Model model, Locale locale) {
        logger.info("Mostrando formulario para nueva participación.");
        try {
            model.addAttribute("participation", new ParticipationCreateDTO());
            model.addAttribute("listPlayers", playerService.listAll());
        } catch (Exception e) {
            logger.error("Error al cargar los jugadores para el formulario de participación: {}", e.getMessage(), e);
            String errorMessage = messageSource.getMessage("msg.participation-controller.edit.error", null, locale);
            model.addAttribute("errorMessage", errorMessage);
        }
        return "views/participation/participation-form";
    }

    /**
     * Muestra el formulario para editar una participación existente.
     *
     * @param id                 ID de la participación a editar.
     * @param model              Modelo para pasar datos a la vista.
     * @param redirectAttributes Atributos para mensajes flash.
     * @param locale             Configuración regional actual.
     * @return plantilla Thymeleaf para el formulario.
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id,
                               Model model,
                               RedirectAttributes redirectAttributes,
                               Locale locale) {

        logger.info("Mostrando formulario de edición para la participación con ID {}", id);

        try {
            // El service encapsula la búsqueda y el mapeo a DTO
            ParticipationUpdateDTO participationDTO = participationService.getForEdit(id);

            model.addAttribute("participation", participationDTO);
            model.addAttribute("listPlayers", playerService.listAll());
            return "views/participation/participation-form";

        } catch (ResourceNotFoundException ex) {
            logger.warn("No se encontró la participación con ID {}", id);
            String msg = messageSource.getMessage("msg.participation-controller.edit.notfound", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/participations";

        } catch (Exception e) {
            logger.error("Error al obtener la participación con ID {}: {}", id, e.getMessage(), e);
            String msg = messageSource.getMessage("msg.participation-controller.edit.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/participations";
        }
    }

    /**
     * Inserta una nueva participación en la base de datos.
     * Spring requiere que {@code BindingResult} vaya justo después del DTO
     * para recoger sus errores de conversión y validación.
     *
     * @param participationDTO   Objeto con los datos del formulario.
     * @param result             Resultado de la validación.
     * @param redirectAttributes Atributos para mensajes flash.
     * @param model              Modelo para recargar el desplegable de jugadores.
     * @param locale             Configuración regional actual para mensajes.
     * @return Redirección al listado de participaciones.
     */
    @PostMapping("/insert")
    public String insertParticipation(@Valid @ModelAttribute("participation") ParticipationCreateDTO participationDTO,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes,
                                      Model model,
                                      Locale locale) {

        logger.info("Insertando nueva participación en la partida {}", participationDTO.getMatchId());

        try {
            // 1) Validaciones propias (reglas que no cubren las anotaciones del DTO)
            validateStats(participationDTO.getKills(), participationDTO.getRoundsPlayed(),
                    participationDTO.getHeadshots(), participationDTO.getTotalHits(), result);

            // 2) BindingResult reúne los errores de campos individuales y los de validateStats.
            if (result.hasErrors()) {
                // Volvemos a cargar los jugadores para el select cuando hay errores
                model.addAttribute("listPlayers", playerService.listAll());
                return "views/participation/participation-form";
            }
            // El service aplica la regla de (partida, jugador) única y persiste
            participationService.create(participationDTO);

            logger.info("Participación en la partida {} insertada con éxito.", participationDTO.getMatchId());
            return "redirect:/participations";

        } catch (DuplicateResourceException ex) {
            // Mostramos el error en el propio formulario para no perder los datos introducidos
            logger.warn("El jugador {} ya tiene una participación en la partida {}.",
                    participationDTO.getPlayerPuuid(), participationDTO.getMatchId());
            result.rejectValue("matchId", "msg.participation-controller.insert.duplicate");
            model.addAttribute("listPlayers", playerService.listAll());
            return "views/participation/participation-form";

        } catch (ResourceNotFoundException ex) {
            logger.warn("No existe el jugador {}: {}", participationDTO.getPlayerPuuid(), ex.getMessage());
            result.rejectValue("playerPuuid", "msg.participation.playerPuuid.notFound");
            model.addAttribute("listPlayers", playerService.listAll());
            return "views/participation/participation-form";

        } catch (Exception e) {
            logger.error("Error al insertar la participación en la partida {}: {}",
                    participationDTO.getMatchId(), e.getMessage(), e);
            String errorMessage = messageSource.getMessage("msg.participation-controller.insert.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/participations/new";
        }
    }

    /**
     * Actualiza una participación existente en la base de datos.
     *
     * @param participationDTO   Participación con los datos actualizados.
     * @param result             Resultado de la validación.
     * @param redirectAttributes Atributos para mensajes flash.
     * @param model              Modelo para recargar el desplegable de jugadores.
     * @param locale             Configuración regional actual.
     * @return Redirección al listado de participaciones.
     */
    @PostMapping("/update")
    public String updateParticipation(@Valid @ModelAttribute("participation") ParticipationUpdateDTO participationDTO,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes,
                                      Model model,
                                      Locale locale) {

        logger.info("Actualizando participación con ID {}", participationDTO.getId());
        try {
            // 1) Validaciones propias
            validateStats(participationDTO.getKills(), participationDTO.getRoundsPlayed(),
                    participationDTO.getHeadshots(), participationDTO.getTotalHits(), result);

            // 2) Al devolver la vista directamente, se conservan los campos enviados y sus errores.
            if (result.hasErrors()) {
                model.addAttribute("listPlayers", playerService.listAll());
                return "views/participation/participation-form";
            }
            // El service aplica reglas (partida + jugador único), comprueba existencia y actualiza
            participationService.update(participationDTO);

            logger.info("Participación con ID {} actualizada con éxito.", participationDTO.getId());
            return "redirect:/participations";

        } catch (DuplicateResourceException ex) {
            logger.warn("El jugador {} ya tiene otra participación en la partida {}.",
                    participationDTO.getPlayerPuuid(), participationDTO.getMatchId());
            result.rejectValue("matchId", "msg.participation-controller.update.duplicate");
            model.addAttribute("listPlayers", playerService.listAll());
            return "views/participation/participation-form";

        } catch (ResourceNotFoundException ex) {
            logger.warn("No se encontró el recurso al actualizar la participación con ID {}: {}",
                    participationDTO.getId(), ex.getMessage());
            String notFound = messageSource.getMessage("msg.participation-controller.detail.notFound", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", notFound);
            return "redirect:/participations";

        } catch (Exception e) {
            logger.error("Error al actualizar la participación con ID {}: {}",
                    participationDTO.getId(), e.getMessage(), e);
            String errorMessage = messageSource.getMessage("msg.participation-controller.update.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/participations/edit?id=" + participationDTO.getId();
        }
    }

    /**
     * Elimina una participación de la base de datos.
     *
     * @param id                 ID de la participación a eliminar.
     * @param redirectAttributes Atributos para mensajes flash.
     * @param locale             Configuración regional actual.
     * @return Redirección al listado de participaciones.
     */
    @PostMapping("/delete")
    public String deleteParticipation(@RequestParam("id") Long id,
                                      RedirectAttributes redirectAttributes,
                                      Locale locale) {

        logger.info("Eliminando participación con ID {}", id);

        try {
            // El service comprueba existencia y elimina (o lanza ResourceNotFoundException)
            participationService.delete(id);

            logger.info("Participación con ID {} eliminada con éxito.", id);
            return "redirect:/participations";

        } catch (ResourceNotFoundException ex) {
            logger.warn("No se encontró la participación con ID {}", id);
            String notFound = messageSource.getMessage("msg.participation-controller.detail.notFound", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", notFound);
            return "redirect:/participations";

        } catch (Exception e) {
            logger.error("Error al eliminar la participación con ID {}: {}", id, e.getMessage(), e);
            String errorMessage = messageSource.getMessage("msg.participation-controller.delete.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
            return "redirect:/participations";
        }
    }

    /**
     * Muestra el detalle de una participación específica, incluyendo su jugador asociado.
     *
     * @param id                 Identificador de la participación a consultar.
     * @param model              Modelo de Spring MVC para pasar datos a la vista.
     * @param redirectAttributes Mensajes flash al redirigir (errores/avisos).
     * @param locale             Configuración regional para internacionalización de mensajes.
     * @return Plantilla Thymeleaf {@code views/participation/participation-detail} o redirección a {@code /participations}.
     */
    @GetMapping("/detail")
    public String showDetail(@RequestParam("id") Long id,
                             Model model,
                             RedirectAttributes redirectAttributes,
                             Locale locale) {

        logger.info("Mostrando detalle de la participación con ID {}", id);

        try {
            // El service se encarga de cargar la participación (con su jugador) y mapear a DTO
            ParticipationDetailDTO participationDTO = participationService.getDetail(id);

            model.addAttribute("participation", participationDTO);
            return "views/participation/participation-detail";

        } catch (ResourceNotFoundException ex) {
            String msg = messageSource.getMessage("msg.participation-controller.detail.notFound", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/participations";

        } catch (Exception e) {
            logger.error("Error al obtener el detalle de la participación {}: {}", id, e.getMessage(), e);
            String msg = messageSource.getMessage("msg.participation-controller.detail.error", null, locale);
            redirectAttributes.addFlashAttribute("errorMessage", msg);
            return "redirect:/participations";
        }
    }

    /**
     * Validaciones propias de las estadísticas que no se pueden expresar con anotaciones
     * porque relacionan varios campos entre sí. Los errores se añaden al {@link BindingResult}
     * con un código de mensaje de messages_xx.properties, igual que los de Bean Validation.
     * Solo se comprueban si los campos implicados no son null (de eso ya se encarga @NotNull).
     */
    private void validateStats(Integer kills, Integer roundsPlayed,
                               Integer headshots, Integer totalHits,
                               BindingResult result) {
        // Los impactos en la cabeza no pueden superar a los impactos totales
        if (headshots != null && totalHits != null && headshots > totalHits) {
            result.rejectValue("headshots", "msg.participation.headshots.greaterThanHits");
        }
        // No se pueden hacer más de 5 bajas por ronda
        if (kills != null && roundsPlayed != null && roundsPlayed > 0
                && kills > roundsPlayed * MAX_KILLS_PER_ROUND) {
            result.rejectValue("kills", "msg.participation.kills.tooMany");
        }
    }
}
