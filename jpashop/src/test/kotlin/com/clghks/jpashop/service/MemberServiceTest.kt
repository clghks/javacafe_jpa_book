package com.clghks.jpashop.service

import com.clghks.jpashop.domain.Member
import com.clghks.jpashop.repository.MemberRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MemberServiceTest(
    @Autowired val memberService: MemberService,
    @Autowired val memberRepository: MemberRepository
) {
    @Test
    fun `회원가입`() {
        // Given
        val member = Member()
        member.name = "kim"

        // When
        val saveId = memberService.join(member)

        // Then
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(saveId))
    }

    @Test
    fun `중복_회원_예외`() {
        // Given
        val member1 = Member()
        member1.name = "kim"

        val member2 = Member()
        member2.name = "kim"

        // When
        memberService.join(member1)
        assertThrows(IllegalStateException::class.java) {
            memberService.join(member2)
        }
    }

}