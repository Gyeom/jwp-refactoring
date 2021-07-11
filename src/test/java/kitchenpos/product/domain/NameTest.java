package kitchenpos.product.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import kitchenpos.product.domain.Name;

class NameTest {

	@DisplayName("이름은 빈문자열일 수 없다.")
	@Test
	void createNameTest() {
		assertAll(
			() -> assertThatThrownBy(() -> Name.valueOf("  "), "이름이 공백만 있은 문자열 일 때 이름 생성 실패해야함")
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("이름은 빈문자열일 수 없습니다."),

			() -> assertThatThrownBy(() -> Name.valueOf(null), "이름이 null 문자열 일 때 이름 생성 실패해야함")
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("이름은 빈문자열일 수 없습니다.")
		);
	}

}