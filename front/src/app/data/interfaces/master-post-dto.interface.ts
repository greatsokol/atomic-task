import {MasterPutDto} from './master-put-dto.interface';
import {DetailsResponseDto} from './details-response.dto';

export interface MasterPostDto extends MasterPutDto {
  details: DetailsResponseDto[];
}
