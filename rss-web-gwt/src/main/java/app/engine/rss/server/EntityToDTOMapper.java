package app.engine.rss.server;

import java.util.ArrayList;
import java.util.List;

import app.engine.rss.entity.FeedEntity;
import app.engine.rss.shared.dto.FeedDTO;

public class EntityToDTOMapper {

	public static FeedDTO getDTO(FeedEntity entity) {
		if (entity == null) {
			return null;
		}
		final FeedDTO dto = new FeedDTO();
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getId());
		dto.setImageUrl(entity.getImageUrl());
		dto.setLink(entity.getLink());
		dto.setTitle(entity.getTitle());
		return dto;
	}

	public static List<FeedDTO> getDTO(List<FeedEntity> entities) {
		if (entities == null) {
			return null;
		}

		final ArrayList<FeedDTO> dtos = new ArrayList<FeedDTO>();

		for (FeedEntity entity : entities) {
			dtos.add(getDTO(entity));
		}
		return dtos;
	}

}
